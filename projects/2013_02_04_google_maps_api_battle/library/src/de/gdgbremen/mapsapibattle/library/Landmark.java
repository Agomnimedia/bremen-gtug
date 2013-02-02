package de.gdgbremen.mapsapibattle.library;

public final class Landmark {

	public final String name;
	
	public final String address;
	
	public AdditionalInformation additionalInformation;
	
	public Landmark(final String name, final String address, final String addInfoId){
		this.name = name;
		this.address = address;
		if(addInfoId != null){
			this.additionalInformation = new AdditionalInformation(addInfoId);
		}
	}
}
