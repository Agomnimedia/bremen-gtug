package de.gdgbremen.mapsapibattle.android.fragments;

public interface MenuActions {

	void marker(boolean show);
	
	void overlays(boolean show);
	
	void position(boolean show);
	
	void route(boolean show);
	
	void changeMapType(int mapType);
}
