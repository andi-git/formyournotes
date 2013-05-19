package at.ahammer.formyournotes.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import at.ahammer.formyournotes.security.EncryptDecrypt;
import at.ahammer.formyournotes.security.NoneEncryptDecrypt;
import at.ahammer.formyournotes.security.PHPEncryptDecrypt;

public class HttpConnector {

	public static enum EncryptionStrategy {
		NONE(new NoneEncryptDecrypt()), PHP_ENCRYP_DECRYPT(
				new PHPEncryptDecrypt());

		private final EncryptDecrypt encryptDecrypt;

		EncryptionStrategy(EncryptDecrypt encryptDecrypt) {
			this.encryptDecrypt = encryptDecrypt;
		}

		public EncryptDecrypt getEncryptDecrypt() {
			return this.encryptDecrypt;
		}

		public String encrypt(String string) throws Exception {
			return encryptDecrypt.bytesToHex(encryptDecrypt.encrypt(string));
		}
	}

	private final String url;

	private final EncryptionStrategy enctyptionStrategy;

	public HttpConnector(String url, EncryptionStrategy encryptionStrategy) {
		this.url = url;
		this.enctyptionStrategy = encryptionStrategy;
	}

	public String doPost(Map<String, String> parameter)
			throws ConnectionException {
		try {
			InputStream inputStream = null;
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url);
				UrlEncodedFormEntity httpEntity = new UrlEncodedFormEntity(
						createNameValuePairs(parameter), "UTF-8");
				httpPost.setEntity(httpEntity);
				httpPost.addHeader("content-type",
						"application/x-www-form-urlencoded; charset=UTF-8");
				HttpResponse response = httpClient.execute(httpPost);
				inputStream = response.getEntity().getContent();
			} catch (Exception e) {
				Log.e("android-common",
						"Error in http connection" + e.toString());
			}
			return convertInputStreamToString(inputStream);
		} catch (Exception e) {
			throw new ConnectionException(e);
		}
	}

	private List<NameValuePair> createNameValuePairs(
			Map<String, String> parameter) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String key : parameter.keySet()) {
			try {
				nameValuePairs.add(new BasicNameValuePair(key,
						enctyptionStrategy.encrypt(parameter.get(key))));
			} catch (Exception e) {
				Log.e("log_tag", "Error creating parameters " + e.toString());
			}
		}
		return nameValuePairs;
	}

	private String convertInputStreamToString(InputStream inputStream) {
		String result = "";
		if (inputStream != null) {
			try {
				StringBuilder sb = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"), 8);
				sb = new StringBuilder();
				sb.append(reader.readLine());
				String line = "0";
				while ((line = reader.readLine()) != null) {
					sb.append("\n" + line);
				}
				inputStream.close();
				result = sb.toString();
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());
			}
		}
		try {
			return new String(enctyptionStrategy.getEncryptDecrypt().decrypt(
					result), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
