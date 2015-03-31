package edu.asu.cse360.team25.client.doctor;

import java.io.IOException;
import java.net.Socket;

import edu.asu.cse360.team25.client.ClientServerConnection;

public class DoctorServerConnection extends ClientServerConnection {

	protected static final int doctorListeningPort = 10231;
	protected static final String serverAddress = "localhost";

	
	public DoctorServerConnection() throws IOException {
		super(serverAddress, doctorListeningPort);
	}



	@Override
	protected void dispatchReceivedMsg(String msg) {
		// TODO Auto-generated method stub
		
	}
	

	
	// message sent from server and received by patient
	
	
	protected void onLoginAck() {
		
	}
	
	protected void onLogoutAck() {
		
	}
	
	protected void onHandleCase() {
		
	}
	
	protected void onQueryCaseAck() {
		
	}
	
	protected void onQueryChatHistoryAck() {
		
	}
	
	protected void onChatMessage() {
		
	}
	
	protected void onQueryPatientProfileAck() {
		
	}

	protected void onQueryLabMeasurementAck() {
		
	}
	
	// message sent from patient and received by server
	
	
	
	protected void sendLogin() {
		
	}
	
	protected void sendLogout() {
		
	}
	
	protected void sendHandleCaseAck() {
		
	}
	
	protected void sendSuspendCase() {
		
	}
	
	protected void sendFinalDiagnose() {
		
	}
	
	protected void sendQueryCase() {
		
	}

	protected void sendQueryChatHistory() {
		
	}
	
	protected void sendChatMessage() {
		
	}
	
	protected void sendQueryPatientProfile() {
		
	}

	protected void sendQueryLabMeasurement() {
		
	}


}
