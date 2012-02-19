package org.gtugs.bremen.multilabyrinth.network.impl.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;
import org.gtugs.bremen.multilabyrinth.network.impl.DefaultNetworkCommunication;
import org.gtugs.bremen.multilabyrinth.scene.api.LevelInformation;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class LevelinformationMessage extends ServerMessage{

	private List<LevelInformation> levelInformations;
	
	@Override
	public short getFlag() {
		return DefaultNetworkCommunication.FLAG_MESSAGE_SERVER_CONNECTION_CLOSE;
	}

	/**
	 * @return the levelInformation
	 */
	public List<LevelInformation> get() {
		return levelInformations;
	}

	/**
	 * @param levelInformation the levelInformation to set
	 */
	public void set(final List<LevelInformation> levelInformations) {
		this.levelInformations = levelInformations;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)
			throws IOException {
		JSONArray array;
		try {
			array = new JSONArray(pDataInputStream.readUTF());
			this.levelInformations = new ArrayList<LevelInformation>(array.length());
			for(int i=0; i<array.length(); i++){
				this.levelInformations.add(new LevelInformation(array.getJSONObject(i)));
			}
		} catch (JSONException e) {
			Log.e("LevelinformationMessage", "JSONException occured: " + e.getMessage());
		}
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)
			throws IOException {
		final JSONArray array = new JSONArray();
		for(int i=0; i<this.levelInformations.size(); i++){
			array.put(this.levelInformations.get(i).toJSON());
		}
		pDataOutputStream.writeUTF(array.toString());
	}

}
