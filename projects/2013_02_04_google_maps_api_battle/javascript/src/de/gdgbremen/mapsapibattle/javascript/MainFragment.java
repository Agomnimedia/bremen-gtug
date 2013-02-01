package de.gdgbremen.mapsapibattle.javascript;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainFragment extends Fragment {

	private WebView webview;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater
				.inflate(R.layout.fragment_main, container, false);
		webview = (WebView) view.findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Log.i("WebView", "(" + errorCode + ")" + description);
			}
		});
		webview.loadUrl("file:///android_asset/map/googlemap.html");
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
}
