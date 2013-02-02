package de.gdgbremen.mapsapibattle.android;

public interface ControlActions {

	void show();
	
	void hide();
	
	void tiltUp();
	
	void tiltDown();
	
	void animateToMountainView();
	
	void animateToBremen();
	
	void traffic(boolean show);
	
	void indoor(boolean show);
}
