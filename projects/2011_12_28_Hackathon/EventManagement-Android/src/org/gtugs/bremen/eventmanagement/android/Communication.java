package org.gtugs.bremen.eventmanagement.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Build;

public abstract class Communication extends
		AsyncTask<JSONObject, Void, JSONObject> {

	private static final String GETPARAM_START = "?";

	public static final String GETPARAM_SEPERATOR = "&";

	public static final String GETPARAM_EQUALSIGN = "=";

	public static final int ERROR_NOERROR = 0;

	public static final int ERROR_CONNECTION = 1;

	public static final int ERROR_JSONHANDLING = 2;

	public static final String ERROR = "ERROR";

	/**
	 * HTTP-Status code 200 for an successfull request.
	 */
	private static final int HTTPSTATE_OK = 200;

	/**
	 * current error code, that can be handled in onCancelled().
	 */
	protected int errorcode = ERROR_NOERROR;

	protected int httpState = 0;

	/**
	 * delay the sending of data.
	 */
	public int sendingDelay = 0;

	public String httpParams = "";

	private static final String SERVERURL = "http://bremengtugeventreg.appspot.com/";
	
	public String servlet = "";

	/**
	 * seperator for useragent string.
	 */
	private static final String UA_SEPERATOR = "; ";

	@Override
	protected final JSONObject doInBackground(final JSONObject... data) {
		if (sendingDelay > 0) {
			try {
				Thread.sleep(sendingDelay);
			} catch (InterruptedException e) {
				cancel(false);
			}
		}
		if (isCancelled()) {
			return null;
		}

		final String userAgent = Build.VERSION.RELEASE + UA_SEPERATOR
				+ Build.MANUFACTURER + UA_SEPERATOR + Build.DEVICE
				+ UA_SEPERATOR + Build.MODEL;
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				userAgent);
		HttpGet httpget = new HttpGet(SERVERURL + servlet + GETPARAM_START + httpParams);
		JSONObject resData = new JSONObject();
		String resString = "";
		try {
			HttpResponse response = httpclient.execute(httpget);
			httpState = response.getStatusLine().getStatusCode();
			if (httpState != HTTPSTATE_OK) {
				errorcode = ERROR_CONNECTION;
				cancel(false);
			}
			resString = inputStreamToString(response.getEntity().getContent())
					.toString();
			resData = new JSONObject(resString);
			if (resData.has(ERROR)) {
				errorcode = resData.getInt(ERROR);
				cancel(false);
			}
		} catch (IOException e) {
			errorcode = ERROR_CONNECTION;
			cancel(false);
		} catch (JSONException e) {
			errorcode = ERROR_JSONHANDLING;
			cancel(false);
		}

		return resData;
	}

	/**
	 * Get the content from a HttpResponse (or any InputStream) as a String.
	 * Quelle:
	 * http://www.androidsnippets.com/get-the-content-from-a-httpresponse
	 * -or-any-inputstream-as-a-string
	 * 
	 * @param is
	 *            input stream
	 * @return the readed string
	 * @throws IOException
	 *             when something failed with inputstream
	 */
	private StringBuilder inputStreamToString(final InputStream is)
			throws IOException {
		String line = "";
		StringBuilder total = new StringBuilder();

		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		// Read response until the end
		while ((line = rd.readLine()) != null) {
			total.append(line);
		}

		// Return full string
		return total;
	}
}
