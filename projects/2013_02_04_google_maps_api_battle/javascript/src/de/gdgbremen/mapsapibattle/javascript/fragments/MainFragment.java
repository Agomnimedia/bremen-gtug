package de.gdgbremen.mapsapibattle.javascript.fragments;

import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import de.gdgbremen.mapsapibattle.javascript.MainActivity;
import de.gdgbremen.mapsapibattle.javascript.R;
import de.gdgbremen.mapsapibattle.library.Landmark;
import de.gdgbremen.mapsapibattle.library.MapType;

public class MainFragment extends Fragment {

	private WebView webview;

	private static final int ANDROID_SDK_HONEYCOMB = 11;

	private Handler handler = new Handler();

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater
				.inflate(R.layout.fragment_main, container, false);
		this.webview = (WebView) view.findViewById(R.id.webview);
		final WebSettings webviewSettings = this.webview.getSettings();
		webviewSettings.setJavaScriptEnabled(true);
		webviewSettings.setGeolocationEnabled(true);
		webview.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onGeolocationPermissionsShowPrompt(String origin,
					Callback callback) {
				// Allow always geolocation permissions for the webview.
				callback.invoke(origin, true, false);
			}

			@Override
			public boolean onConsoleMessage(ConsoleMessage cm) {
				switch (cm.messageLevel()) {
					case DEBUG:
						Log.d("MapsWebView-DEBUG", cm.message() + " -- " + cm.lineNumber()
								+ "@" + cm.sourceId().substring(26));
						break;
					case ERROR:
						Log.e("MapsWebView-ERROR", cm.message() + " -- " + cm.lineNumber()
								+ "@" + cm.sourceId().substring(26));
						break;
					case LOG:
						Log.i("MapsWebView-LOG", cm.message() + " -- " + cm.lineNumber()
								+ "@" + cm.sourceId().substring(26));
						break;
					case TIP:
						Log.i("MapsWebView-TIP", cm.message() + " -- " + cm.lineNumber()
								+ "@" + cm.sourceId().substring(26));
						break;
					case WARNING:
						Log.w("MapsWebView-WARN", cm.message() + " -- " + cm.lineNumber()
								+ "@" + cm.sourceId().substring(26));
						break;
					default:
						break;
				}
				return true;
			}
		});
		if (Build.VERSION.SDK_INT >= ANDROID_SDK_HONEYCOMB) {
			this.webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		webview.addJavascriptInterface(new JSInterface(), "androidapp");
		webview.loadUrl("file:///android_asset/map/googlemap.html");
		return view;
	}

	/**
	 * One object of this class will be injected on the WebView as an object of
	 * JavaScript. So JavaScript can communicate with the native Android App
	 * through this object.
	 */
	private class JSInterface {

		private int geocoderProgressSumCount = 0;
		private int geocoderProgressCurrentState = 0;

		/**
		 * return an JSON formatted strign with all landmarks and its additional
		 * informations.
		 * 
		 * @return the JSON array.
		 */
		@JavascriptInterface
		public String getLandmarks() {
			final MainActivity activity = (MainActivity) getActivity();
			final List<Landmark> landmarks = activity.getLandmarks();
			final JSONArray json = new JSONArray();
			for (Landmark landmark : landmarks) {
				json.put(landmark.toJSON());
			}
			return json.toString();
		}

		@JavascriptInterface
		public void startGeocoderSearching(int sumCount) {
			geocoderProgressSumCount = sumCount;
			Log.i("startGeocoderSearching", "sumCount: " + sumCount);
			// dialogHandler.sendEmptyMessage(sumCount);
			handler.post(new Runnable() {

				@Override
				public void run() {
					final MainActivity activity = (MainActivity)getActivity();
					activity.showGeocodingProgress(geocoderProgressSumCount);
				}
			});
		}

		@JavascriptInterface
		public void setGeocoderSearchingState(int plusCount) {
			geocoderProgressCurrentState += plusCount;
			handler.post(new Runnable() {

				@Override
				public void run() {
					final MainActivity activity = (MainActivity)getActivity();
					activity.setGeocodingProgressState(geocoderProgressCurrentState);
				}
			});
			if (geocoderProgressCurrentState == geocoderProgressSumCount) {
			}
		}
	}

	public void showMarker() {
		webview.loadUrl("javascript:showMarker()");
	}

	public void hideMarker() {
		webview.loadUrl("javascript:hideMarker()");
	}

	public void showOverlays() {
		webview.loadUrl("javascript:showOverlays()");
	}

	public void hideOverlays() {
		webview.loadUrl("javascript:hideOverlays()");
	}

	public void showPosition() {
		webview.loadUrl("javascript:showPosition()");
	}

	public void hidePosition() {
		webview.loadUrl("javascript:hidePosition()");
	}

	public void setMapType(MapType mapType) {
		webview.loadUrl("javascript:setMapType('" + mapType + "')");
	}
}
