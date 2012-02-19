package org.gtugs.bremen.multilabyrinth.network.impl.messages;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.andengine.extension.multiplayer.protocol.adt.message.server.ServerMessage;

public class ConnectionCloseServerMessage extends ServerMessage{

	@Override
	public short getFlag() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void onReadTransmissionData(DataInputStream pDataInputStream)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onWriteTransmissionData(DataOutputStream pDataOutputStream)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

}
