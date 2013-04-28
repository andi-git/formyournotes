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
import at.ahammer.formyournotes.http.HttpConnector;
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
							sendToServer
									.add(createServerData(singleFileStatus));
						} else {
							getFromServer.add(serverData);
						}
					}
				}
				List<SingleFileStatus> localCreated = getAllLocalCreatedFiles(serverDataList);
				for (SingleFileStatus singleFileStatus : localCreated) {
					sendToServer.add(createServerData(singleFileStatus));
				}
				saveDataFromServer(getFromServer(getFromServer));
				sendToServer(sendToServer);
			} catch (SyncException e) {
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
				Log.i(LogTag.FYN.getTag(), "update file: " + serverData.getFilename());
				// update fileStatus
				SingleFileStatus singleFileStatus = new SingleFileStatus(
						serverData.getFilename(), serverData.getHash(),
						serverData.getTimestamp());
				fileStatus.setSingleFileStatus(singleFileStatus);
				Log.i(LogTag.FYN.getTag(), "update file-status for file: " + serverData.getFilename());
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
		return doPost(URL_ITEMS_WITH_CONTENT, parameters);
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

	private void sendToServer(List<ServerData> serverDataList)
			throws SyncException {
		try {
			Log.i(LogTag.FYN.getTag(), "send files to server");
			for (ServerData serverData : serverDataList) {
				Log.i(LogTag.FYN.getTag(), "    " + serverData.getFilename());
			}
			Map<String, String> parameters = getParametersWithAccountInfo();
			String items = new JSONBeanSerializer().serialize(serverDataList
					.toArray());
			// TODO problem: the hash-code is not correct when removing
			// line-breaks
			items = items.replaceAll("\n", "");
			parameters.put("items", items);
			parameters.put("persist", "true");
			doPost(URL_ADD_ITEMS, parameters);
		} catch (SerializationException e) {
			throw new SyncException(e);
		}
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
		return result;
	}

	private boolean hashEquals(SingleFileStatus singleFileStatus,
			ServerData serverData) {
		boolean result = false;
		if (singleFileStatus != null && serverData != null
				&& serverData.getHash() != null) {
			serverData.getHash().equals(singleFileStatus.getHash());
		}
		Log.i(LogTag.FYN.getTag(), "hash of " + singleFileStatus.getFileName()
				+ " equals: " + result);
		return result;
	}

	private SingleFileStatus getLocalFileStatus(ServerData serverData) {
		return FYNController.INSTANCE.getSingleFileStatus(context,
				serverData.getFilename());
	}

	private List<ServerData> getAllServerDataWithoutContent()
			throws SyncException {
		return doPost(URL_ITEMS_WITHOUT_CONTENT, getParametersWithAccountInfo());
	}

	private List<ServerData> doPost(String url, Map<String, String> parameters)
			throws SyncException {
		try {
			Log.i(LogTag.FYN.getTag(), "call " + url + " with paramters");
			for (String key : parameters.keySet()) {
				Log.i(LogTag.FYN.getTag(),
						"    " + key + " -> " + parameters.get(key));
			}
			HttpConnector connector = new HttpConnector(url);
			String result = connector.doPost(parameters);
			BeanSerializer jsonBeanSerializer = new JSONBeanSerializer();
			ServerData[] serverData = jsonBeanSerializer.deserialize(result,
					ServerData[].class);
			Log.i(LogTag.FYN.getTag(), "return ServerData");
			for (ServerData currentServerData : serverData) {
				Log.i(LogTag.FYN.getTag(), currentServerData.toString());
			}
			return Arrays.asList(serverData);
		} catch (Exception e) {
			throw new SyncException(e);
		}
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
}
