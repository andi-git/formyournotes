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
import at.ahammer.formyournotes.security.PHPEncryptDecrypt;

public class HttpConnector {

	private final String url;

	private final PHPEncryptDecrypt phpEncryptDecrypt = new PHPEncryptDecrypt();

	public HttpConnector(String url) {
		this.url = url;
	}

	public String doPost(Map<String, String> parameter) {
		InputStream inputStream = null;
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(
					createNameValuePairs(parameter)));
			HttpResponse response = httpclient.execute(httppost);
			inputStream = response.getEntity().getContent();
		} catch (Exception e) {
			Log.e("android-common", "Error in http connection" + e.toString());
		}
		return convertInputStreamToString(inputStream);
	}

	private List<NameValuePair> createNameValuePairs(
			Map<String, String> parameter) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		for (String key : parameter.keySet()) {
			try {
				nameValuePairs.add(new BasicNameValuePair(key,
						PHPEncryptDecrypt.bytesToHex(phpEncryptDecrypt
								.encrypt(parameter.get(key)))));
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
						new InputStreamReader(inputStream, "iso-8859-1"), 8);
				sb = new StringBuilder();
				sb.append(reader.readLine() + "\n");
				String line = "0";
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				inputStream.close();
				result = sb.toString();
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());
			}
		}
		try {
			return new String(phpEncryptDecrypt.decrypt(result), "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
