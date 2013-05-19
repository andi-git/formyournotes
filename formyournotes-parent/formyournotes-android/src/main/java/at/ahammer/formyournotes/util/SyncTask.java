package at.ahammer.formyournotes.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import at.ahammer.formyournotes.beans.FileStatus;
import at.ahammer.formyournotes.beans.ServerData;
import at.ahammer.formyournotes.beans.SingleFileStatus;
import at.ahammer.formyournotes.beanserializer.BeanSerializer;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.beanserializer.SerializationException;
import at.ahammer.formyournotes.http.ConnectionException;
import at.ahammer.formyournotes.http.HttpConnector;
import at.ahammer.formyournotes.http.HttpConnector.EncryptionStrategy;
import at.ahammer.formyournotes.http.NetworkDetector;
import at.ahammer.formyournotes.http.NetworkDetector.ConnectionType;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.util.FYNPreferences.Account;

public class SyncTask extends AsyncTask<Void, Void, Boolean> {

	private static final String PARAMETER_PASSWORD = "password";
	private static final String PARAMETER_EMAIL = "email";
	private static final String URL_ITEMS_WITHOUT_CONTENT = "http://www.eppel-boote.at/fyn/itemsForUserWithoutContent.php";
	private static final String URL_ITEMS_WITH_CONTENT = "http://www.eppel-boote.at/fyn/itemsForUserWithContent.php";
	private static final String URL_ADD_ITEMS = "http://www.eppel-boote.at/fyn/addItems.php";
	private final Context context;

	public SyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Void... nothing) {
		boolean success = true;
		if (hasNetworkConnection()) {
			try {
				List<ServerData> serverDataList = getAllServerDataWithoutContent();
				List<ServerData> sendToServer = new ArrayList<ServerData>();
				List<ServerData> getFromServer = new ArrayList<ServerData>();
				for (ServerData serverData : serverDataList) {
					SingleFileStatus singleFileStatus = getLocalFileStatus(serverData);
					if (!hashEquals(singleFileStatus, serverData)) {
						if (isLocalNewer(singleFileStatus, serverData)) {
							Log.i(LogTag.FYN.getTag(), "add to sendToServer: "
									+ singleFileStatus.getFileName());
							sendToServer
									.add(createServerData(singleFileStatus));
						} else {
							Log.i(LogTag.FYN.getTag(), "add to getFromServer: "
									+ serverData.getFilename());
							getFromServer.add(serverData);
						}
					}
				}
				List<SingleFileStatus> localCreated = getAllLocalCreatedFiles(serverDataList);
				for (SingleFileStatus singleFileStatus : localCreated) {
					Log.i(LogTag.FYN.getTag(),
							"local created -> add to sendToServer: "
									+ singleFileStatus.getFileName());
					sendToServer.add(createServerData(singleFileStatus));
				}
				saveDataFromServer(getFromServer(getFromServer));
				if (!sendToServer(sendToServer)) {
					throw new SyncException("send to server returned failure");
				}
			} catch (SyncException e) {
				Log.e(LogTag.FYN.getTag(),
						"general sync error: " + e.getMessage(), e);
				success = false;
			}
		} else {
			success = false;
		}
		return success;
	}

	private void saveDataFromServer(List<ServerData> serverDataList)
			throws SyncException {
		try {
			FileStatus fileStatus = FYNController.INSTANCE
					.getFileStatus(context);
			for (ServerData serverData : serverDataList) {
				// save in file
				FYNFileHelper.INSTANCE.saveFileContent(context,
						serverData.getFilename(), serverData.getContent());
				Log.i(LogTag.FYN.getTag(),
						"update file: " + serverData.getFilename());
				// update fileStatus
				SingleFileStatus singleFileStatus = new SingleFileStatus(
						serverData.getFilename(), serverData.getHash(),
						serverData.getTimestamp());
				fileStatus.setSingleFileStatus(singleFileStatus);
				Log.i(LogTag.FYN.getTag(), "update file-status for file: "
						+ serverData.getFilename());
			}
			// save filestatus
			FYNController.INSTANCE.saveFileStatus(context, fileStatus);
			Log.i(LogTag.FYN.getTag(), "save file-status");
		} catch (Exception e) {
			throw new SyncException(e);
		}
	}

	private List<ServerData> getFromServer(List<ServerData> getFromServer)
			throws SyncException {
		List<ServerData> result = new ArrayList<ServerData>();
		if (getFromServer != null && !getFromServer.isEmpty()) {
			Log.i(LogTag.FYN.getTag(), "get from server: ");
			Map<String, String> parameters = getParametersWithAccountInfo();
			StringBuilder fileNames = new StringBuilder();
			boolean first = true;
			for (ServerData serverData : getFromServer) {
				Log.i(LogTag.FYN.getTag(), "    " + serverData.getFilename());
				if (!first) {
					fileNames.append(",");
				}
				fileNames.append(serverData.getFilename());
				first = false;
			}
			parameters.put("filenames", fileNames.toString());
			Log.i(LogTag.FYN.getTag(),
					"parameter filenames: " + fileNames.toString());
			result = doPost(URL_ITEMS_WITH_CONTENT, parameters,
					new ServerResultServerData());
		}
		return result;
	}

	private List<SingleFileStatus> getAllLocalCreatedFiles(
			List<ServerData> serverDataList) {
		List<SingleFileStatus> localCreatedFile = new ArrayList<SingleFileStatus>();
		for (SingleFileStatus singleFileStatus : FYNController.INSTANCE
				.getFileStatus(context).getFiles()) {
			String localFile = singleFileStatus.getFileName();
			boolean isInServerDataList = false;
			for (ServerData serverData : serverDataList) {
				if (serverData.getFilename().equals(localFile)) {
					isInServerDataList = true;
					break;
				}
			}
			if (!isInServerDataList) {
				localCreatedFile.add(singleFileStatus);
			}
		}
		Log.i(LogTag.FYN.getTag(), "local created files: ");
		for (SingleFileStatus singleFileStatus : localCreatedFile) {
			Log.i(LogTag.FYN.getTag(), "    " + singleFileStatus.getFileName());
		}
		return localCreatedFile;
	}

	private boolean sendToServer(List<ServerData> serverDataList)
			throws SyncException {
		boolean result = true;
		if (serverDataList != null && !serverDataList.isEmpty()) {
			try {
				Log.i(LogTag.FYN.getTag(), "send files to server");
				for (ServerData serverData : serverDataList) {
					Log.i(LogTag.FYN.getTag(),
							"    " + serverData.getFilename());
				}
				Map<String, String> parameters = getParametersWithAccountInfo();
				String items = new JSONBeanSerializer()
						.serialize(serverDataList.toArray());
				parameters.put("items", items);
				parameters.put("persist", "true");
				result = doPost(URL_ADD_ITEMS, parameters,
						new ServerResultSuccess());
			} catch (SerializationException e) {
				throw new SyncException(e);
			}
		}
		return result;
	}

	private ServerData createServerData(SingleFileStatus singleFileStatus)
			throws SyncException {
		ServerData serverData = new ServerData();
		try {
			serverData.setContent(FYNFileHelper.INSTANCE.getFileContent(
					context, singleFileStatus.getFileName()));
		} catch (Exception e) {
			throw new SyncException(e);
		}
		serverData.setFilename(singleFileStatus.getFileName());
		serverData.setHash(singleFileStatus.getHash());
		serverData.setTimestamp(singleFileStatus.getTimestamp());
		return serverData;
	}

	private boolean isLocalNewer(SingleFileStatus singleFileStatus,
			ServerData serverData) {
		boolean result = false;
		if (singleFileStatus != null && serverData != null) {
			result = singleFileStatus.getTimestamp() > serverData
					.getTimestamp();
		}
		Log.i(LogTag.FYN.getTag(),
				"local file " + singleFileStatus.getFileName() + " is newer: "
						+ result);
		Log.i(LogTag.FYN.getTag(),
				"    local:  " + singleFileStatus.getTimestamp());
		Log.i(LogTag.FYN.getTag(), "    server: " + serverData.getTimestamp());
		return result;
	}

	private boolean hashEquals(SingleFileStatus singleFileStatus,
			ServerData serverData) {
		boolean result = false;
		if (singleFileStatus != null && serverData != null
				&& serverData.getHash() != null) {
			result = serverData.getHash().equals(singleFileStatus.getHash());
		}
		Log.i(LogTag.FYN.getTag(), "hash of " + singleFileStatus.getFileName()
				+ " equals: " + result);
		Log.i(LogTag.FYN.getTag(), "    local:  " + singleFileStatus.getHash());
		Log.i(LogTag.FYN.getTag(), "    server: " + serverData.getHash());
		return result;
	}

	private SingleFileStatus getLocalFileStatus(ServerData serverData) {
		return FYNController.INSTANCE.getSingleFileStatus(context,
				serverData.getFilename());
	}

	private List<ServerData> getAllServerDataWithoutContent()
			throws SyncException {
		return doPost(URL_ITEMS_WITHOUT_CONTENT,
				getParametersWithAccountInfo(), new ServerResultServerData());
	}

	private <T> T doPost(String url, Map<String, String> parameters,
			ServerResultHandler<T> serverResultHandler) throws SyncException {
		Log.i(LogTag.FYN.getTag(), "call " + url + " with paramters");
		for (String key : parameters.keySet()) {
			Log.i(LogTag.FYN.getTag(),
					"    " + key + " -> " + parameters.get(key));
		}
		HttpConnector connector = new HttpConnector(url,
				EncryptionStrategy.NONE);
		String serverResult = "";
		try {
			serverResult = connector.doPost(parameters);
		} catch (ConnectionException e) {
			throw new SyncException(e);
		}
		return serverResultHandler.getServerResult(serverResult);
	}

	@Override
	protected void onPostExecute(Boolean success) {
		// nothing
	}

	private Map<String, String> getParametersWithAccountInfo() {
		Map<String, String> parameters = new HashMap<String, String>();
		Account account = FYNPreferences.INSTANCE.getAccount(context);
		Log.i(LogTag.FYN.getTag(), "account valid: " + account.isValid());
		Log.i(LogTag.FYN.getTag(), "account email: " + account.getEmail());
		Log.i(LogTag.FYN.getTag(),
				"account password: " + account.getPasswordHash());
		if (account.isValid()) {
			parameters.put(PARAMETER_EMAIL, account.getEmail());
			parameters.put(PARAMETER_PASSWORD, account.getPasswordHash());
		}
		return parameters;
	}

	private boolean hasNetworkConnection() {
		return new NetworkDetector().hasNetworkConnection(context,
				ConnectionType.ANY);
	}

	private static interface ServerResultHandler<T> {
		T getServerResult(String httpResult) throws SyncException;
	}

	private static class ServerResultServerData implements
			ServerResultHandler<List<ServerData>> {

		@Override
		public List<ServerData> getServerResult(String httpResult)
				throws SyncException {
			List<ServerData> result = new ArrayList<ServerData>();
			if (httpResult != null && !"".equals(httpResult)) {
				BeanSerializer jsonBeanSerializer = new JSONBeanSerializer();
				ServerData[] serverData = null;
				try {
					serverData = jsonBeanSerializer.deserialize(httpResult,
							ServerData[].class);
				} catch (SerializationException e) {
					throw new SyncException(e);
				}
				Log.i(LogTag.FYN.getTag(), "return ServerData");
				if (serverData != null) {
					for (ServerData currentServerData : serverData) {
						Log.i(LogTag.FYN.getTag(), currentServerData.toString());
					}
					result = Arrays.asList(serverData);
				}
			}
			return result;
		}

	}

	private static class ServerResultSuccess implements
			ServerResultHandler<Boolean> {

		@Override
		public Boolean getServerResult(String httpResult) throws SyncException {
			Log.i(LogTag.FYN.getTag(), "server result: " + httpResult);
			return httpResult != null && "success".equalsIgnoreCase(httpResult);
		}
	}
}
