package at.ahammer.formyournotes.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import at.ahammer.formyournotes.beans.FileWriteActivity;
import at.ahammer.formyournotes.beans.ServerData;
import at.ahammer.formyournotes.beans.ServerLastUpdate;
import at.ahammer.formyournotes.beans.SingleFileStatus;
import at.ahammer.formyournotes.beanserializer.JSONBeanSerializer;
import at.ahammer.formyournotes.http.ConnectionException;
import at.ahammer.formyournotes.http.HttpConnector;
import at.ahammer.formyournotes.http.NetworkDetector;
import at.ahammer.formyournotes.http.NetworkDetector.ConnectionType;
import at.ahammer.formyournotes.logging.LogTag;
import at.ahammer.formyournotes.util.FYNPreferences.Account;

public class SyncTask extends AsyncTask<Void, Void, Boolean> {

	private static final String PARAMETER_PASSWORD = "password";
	private static final String PARAMETER_EMAIL = "email";
	private static final String URL_LAST_UPDATE = "http://www.eppel-boote.at/fyn/lastUpdate.php";
	private static final String URL_ITEMS_UPDATED_SINCE = "http://www.eppel-boote.at/fyn/itemsUpdatedSince.php";
	private final Context context;

	public SyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(Void... nothing) {
		boolean success = true;
		if (hasNetworkConnection(context)) {
			try {
				if (true) {
					List<ServerData> serverDataList = getAllServerDataWithoutContent();
					List<ServerData> sendToServer = new ArrayList<ServerData>();
					List<ServerData> getFromServer = new ArrayList<ServerData>();
					for (ServerData serverData : serverDataList) {
						SingleFileStatus singleFileStatus = getLocalFileStatus(serverData);
						if (!hashEquals(singleFileStatus, serverData)) {
							if (isLocalNewer(singleFileStatus, serverData)) {
								sendToServer(createServerData(singleFileStatus));
							} else {
								getFromServer.add(serverData);
							}
						}
					}
					List<SingleFileStatus> localCreated = getAllLocalCreatedFiles();
					for (SingleFileStatus singleFileStatus : localCreated) {
						sendToServer(createServerData(singleFileStatus));
					}
					getFromServer(getFromServer);
					sendToServer(sendToServer);
				} else {
					long lastSync = FYNController.INSTANCE.getLastSync(context);
					Log.i(LogTag.FYN.getTag(), "lastSync: " + lastSync);
					long lastUpdateOnServer;
					lastUpdateOnServer = lastUpdateOnServer(context);
					Log.i(LogTag.FYN.getTag(), "lastUpdateOnServer: "
							+ lastUpdateOnServer);
					// check if there are local changes since last server-update
					List<FileWriteActivity> localItemsSinceLastUpdate = localChangesSinceLastUpdateOnServer(lastUpdateOnServer);
					boolean localChangesSinceLastUpdateOnServer = localItemsSinceLastUpdate
							.isEmpty();
					ServerData[] serverItemsSinceLastUdpate = serverChangesSinceLastLocalUpdate(
							lastSync, context);
					boolean serverChangesSinceLastLocalUpdate = serverItemsSinceLastUdpate == null
							|| serverItemsSinceLastUdpate.length == 0;
					long newTimestamp = Calendar.getInstance(
							TimeZone.getTimeZone("Europe/Berlin"))
							.getTimeInMillis();
					if (localChangesSinceLastUpdateOnServer
							&& serverChangesSinceLastLocalUpdate) {
						// shit, a conflict!
					} else if (localChangesSinceLastUpdateOnServer) {
						// the local version is newer
						ServerData[] sendUpdatedToServer = new ServerData[localItemsSinceLastUpdate
								.size()];
						for (int i = 0; i < localItemsSinceLastUpdate.size(); i++) {
							FileWriteActivity fileWriteActivity = localItemsSinceLastUpdate
									.get(i);
							ServerData serverData = new ServerData();
							serverData.setContent(FYNFileHelper.INSTANCE
									.getFileContent(context,
											fileWriteActivity.getFileName()));
							serverData.setFilename(fileWriteActivity
									.getFileName());
							serverData.setTimestamp(newTimestamp);
							sendUpdatedToServer[i] = serverData;
						}
					} else if (serverChangesSinceLastLocalUpdate) {
						// the server version is newer
					}
				}
			} catch (SyncException e) {
				success = false;
			}
		} else {
			success = false;
		}
		return success;
	}

	private void getFromServer(List<ServerData> getFromServer) {
		// TODO Auto-generated method stub
		
	}

	private List<SingleFileStatus> getAllLocalCreatedFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	private void sendToServer(Object createServerData) {
		// TODO Auto-generated method stub
		
	}

	private Object createServerData(SingleFileStatus singleFileStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isLocalNewer(SingleFileStatus singleFileStatus,
			ServerData serverData) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean hashEquals(SingleFileStatus singleFileStatus,
			ServerData serverData) {
		// TODO Auto-generated method stub
		return false;
	}

	private SingleFileStatus getLocalFileStatus(ServerData serverData) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<ServerData> getAllServerDataWithoutContent() {
		// TODO Auto-generated method stub
		return null;
	}

	private ServerData[] serverChangesSinceLastLocalUpdate(long lastSync,
			Context context) throws SyncException {
		ServerData[] itemsSinceLastUdpate = new ServerData[0];
		Map<String, String> parameters = new HashMap<String, String>();
		addAccountInfo(context, parameters);
		HttpConnector connector = new HttpConnector(URL_ITEMS_UPDATED_SINCE);
		try {
			String result = connector.doPost(parameters);
			Log.i(LogTag.FYN.getTag(), "http-result from " + URL_LAST_UPDATE
					+ ": " + result);
			try {
				ServerData[] itemsSinceLastUpdate = new JSONBeanSerializer()
						.deserialize(result, ServerData[].class);
				Log.i(LogTag.FYN.getTag(), "itemsSinceLastUpdate: "
						+ itemsSinceLastUpdate);
			} catch (Exception e) {
				Log.e(LogTag.FYN.getTag(), "error on deserializing LastUpdate",
						e);
				throw new SyncException(e);
			}

		} catch (ConnectionException e) {
			Log.e(LogTag.FYN.getTag(), "connection error", e);
			throw new SyncException(e);
		}
		return itemsSinceLastUdpate;
	}

	private List<FileWriteActivity> localChangesSinceLastUpdateOnServer(
			long lastUdateOnServer) {
		return FYNController.INSTANCE.getFileWriteActivitesAfter(context,
				lastUdateOnServer);
	}

	@Override
	protected void onPostExecute(Boolean success) {
		// nothing
	}

	private long lastUpdateOnServer(Context context) throws SyncException {
		long lastUpdateOnServer = 0L;
		Map<String, String> parameters = new HashMap<String, String>();
		addAccountInfo(context, parameters);
		HttpConnector connector = new HttpConnector(URL_LAST_UPDATE);
		String result;
		try {
			result = connector.doPost(parameters);
			Log.i(LogTag.FYN.getTag(), "http-result from " + URL_LAST_UPDATE
					+ ": " + result);
			try {
				ServerLastUpdate[] lastUpdates = new JSONBeanSerializer()
						.deserialize(result, ServerLastUpdate[].class);
				Log.i(LogTag.FYN.getTag(), "lastUpdates: " + lastUpdates);
				if (lastUpdates != null && lastUpdates.length > 0) {
					lastUpdateOnServer = lastUpdates[0].getLastUpdate();
				}
			} catch (Exception e) {
				Log.e(LogTag.FYN.getTag(), "error on deserializing LastUpdate",
						e);
				throw new SyncException(e);
			}
		} catch (ConnectionException e) {
			Log.e(LogTag.FYN.getTag(), "connection error", e);
			throw new SyncException(e);
		}
		return lastUpdateOnServer;
	}

	private void addAccountInfo(Context context, Map<String, String> parameters) {
		Account account = FYNPreferences.INSTANCE.getAccount(context);
		Log.i(LogTag.FYN.getTag(), "account valid: " + account.isValid());
		Log.i(LogTag.FYN.getTag(), "account email: " + account.getEmail());
		Log.i(LogTag.FYN.getTag(),
				"account password: " + account.getPasswordHash());
		if (account.isValid()) {
			parameters.put(PARAMETER_EMAIL, account.getEmail());
			parameters.put(PARAMETER_PASSWORD, account.getPasswordHash());
		}
	}

	private boolean hasNetworkConnection(Context context) {
		return new NetworkDetector().hasNetworkConnection(context,
				ConnectionType.ANY);
	}
}
