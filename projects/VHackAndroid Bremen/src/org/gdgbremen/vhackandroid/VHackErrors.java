package org.gdgbremen.vhackandroid;

import java.util.HashMap;
import java.util.Map;

import de.liedtke.presentation.ErrorMessages;

public class VHackErrors implements ErrorMessages{

private final Map<Integer,String> errorMessages = new HashMap<Integer, String>();
	
	public VHackErrors(){
		errorMessages.put(Integer.valueOf(88), "An json exception occured!  Please contact the admin");
	}
	
	@Override
	public String getMessage(final int code) {
		String result = errorMessages.get(Integer.valueOf(code));
		if(result == null){
			result = "Errormessage couldn't be found! Please ask the admin";
		}
		return result;
	}
}
