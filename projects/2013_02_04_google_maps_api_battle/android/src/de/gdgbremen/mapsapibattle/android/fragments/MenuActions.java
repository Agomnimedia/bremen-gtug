package de.gdgbremen.mapsapibattle.android.fragments;

import java.util.List;

import de.gdgbremen.mapsapibattle.library.Landmark;

public interface MenuActions {
	
	void marker(List<Landmark> landmarks);
	
	void overlays(boolean show);
	
	void position(boolean show);
	
	void changeMapType(int mapType);
}
