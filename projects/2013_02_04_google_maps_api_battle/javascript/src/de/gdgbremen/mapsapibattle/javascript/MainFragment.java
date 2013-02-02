package de.gdgbremen.mapsapibattle.javascript;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
		this.webview.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Log.i("WebView", "(" + errorCode + ")" + description);
			}
		});
		webview.loadUrl("file:///android_asset/map/googlemap.html");
		if (Build.VERSION.SDK_INT >= ANDROID_SDK_HONEYCOMB) {
			this.webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		return view;
	}

	public void showMarker() {
		// TODO Auto-generated method stub

	}

	public void hideMarker() {
		// TODO Auto-generated method stub

	}

	public void showOverlays() {
		// TODO Auto-generated method stub

	}

	public void hideOverlays() {
		// TODO Auto-generated method stub

	}

	public void showPosition() {
		// TODO Auto-generated method stub

	}

	public void hidePosition() {
		// TODO Auto-generated method stub

	}

	public void showRoute() {
		// TODO Auto-generated method stub

	}

	public void hideRoute() {
		// TODO Auto-generated method stub

	}

	public void setMapType(MapType mapType) {
		webview.loadUrl("javascript:setMapType('" + mapType + "')");
	}
}
