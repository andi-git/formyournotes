package at.ahammer.formyournotes.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkDetector {

	public static enum ConnectionType {
		ANY {
			@Override
			boolean isType(NetworkInfo networkInfo) {
				return MOBILE.isType(networkInfo) || WIFI.isType(networkInfo);
			}
		},
		MOBILE {
			@Override
			boolean isType(NetworkInfo networkInfo) {
				return networkInfo != null
						&& "MOBILE".equalsIgnoreCase(networkInfo.getTypeName());
			}
		},
		WIFI {
			@Override
			boolean isType(NetworkInfo networkInfo) {
				return networkInfo != null
						&& "WIFI".equalsIgnoreCase(networkInfo.getTypeName());
			}
		};

		public boolean isConnected(ConnectivityManager connectivityManager) {
			NetworkInfo[] networkInfoArray = connectivityManager.getAllNetworkInfo();
			for (NetworkInfo networkInfo : networkInfoArray) {
				if (isType(networkInfo) && networkInfo.isConnected()) {
					return true;
				}
			}
			return false;
		}

		abstract boolean isType(NetworkInfo networkInfo);
	}

	public boolean hasNetworkConnection(Context context,
			ConnectionType connectionType) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return connectionType.isConnected(connectivityManager);
	}
}
