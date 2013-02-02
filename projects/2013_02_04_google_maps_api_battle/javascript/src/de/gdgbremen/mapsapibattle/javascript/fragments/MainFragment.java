package de.gdgbremen.mapsapibattle.javascript.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import de.gdgbremen.mapsapibattle.javascript.R;
import de.gdgbremen.mapsapibattle.library.MapType;

public class MainFragment extends Fragment {

	private WebView webview;

	private static final int ANDROID_SDK_HONEYCOMB = 11;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater
				.inflate(R.layout.fragment_main, container, false);
		this.webview = (WebView) view.findViewById(R.id.webview);
		this.webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebChromeClient(new WebChromeClient() {
			public boolean onConsoleMessage(ConsoleMessage cm) {
				switch(cm.messageLevel()) {
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
		webview.loadUrl("file:///android_asset/map/googlemap.html");
		if (Build.VERSION.SDK_INT >= ANDROID_SDK_HONEYCOMB) {
			this.webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		return view;
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

	public void showRoute() {
		webview.loadUrl("javascript:showRoute()");
	}

	public void hideRoute() {
		webview.loadUrl("javascript:hideRoute()");
	}

	public void setMapType(MapType mapType) {
		webview.loadUrl("javascript:setMapType('" + mapType + "')");
	}
}
