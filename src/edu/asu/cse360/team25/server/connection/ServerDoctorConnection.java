package edu.asu.cse360.team25.server.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import edu.asu.cse360.team25.server.Case;
import edu.asu.cse360.team25.server.CaseManager;
import edu.asu.cse360.team25.server.Chat;
import edu.asu.cse360.team25.server.ChatManager;
import edu.asu.cse360.team25.server.DoctorManager;
import edu.asu.cse360.team25.server.LabManager;
import edu.asu.cse360.team25.server.LabMeasurement;
import edu.asu.cse360.team25.server.NurseManager;
import edu.asu.cse360.team25.server.Patient;
import edu.asu.cse360.team25.server.PatientManager;
import edu.asu.cse360.team25.server.Server;

public class ServerDoctorConnection extends ServerClientConnection {


	public ServerDoctorConnection(Socket socket, Server server) throws IOException {
		super(socket, server);


	}




	@Override
	protected void dispatchReceivedMsg(String msg) {
		// TODO Auto-generated method stub
		
		
		
		
	}

	
	
	
	// message sent from server and received by patient
	
	
	protected void sendLoginAck() throws IOException {
		
		sendMsg("xxx");
	}
	
	protected void sendLogoutAck() throws IOException {
		
		sendMsg("xxx");
	}
	
	protected void sendHandleCase(Case c) throws IOException {
		
		sendMsg("xxx");
	}
	
	protected void sendQueryCaseAck(Case c) throws IOException {
		
		sendMsg("xxx");
	}
	
	protected void sendQueryChatHistoryAck(List<Chat> chats) throws IOException {
		
		sendMsg("xxx");
	}
	
	protected void forwardChatMessage(String msg) throws IOException {
		
		sendMsg("xxx");
	}
	
	protected void sendQueryPatientProfileAck(Patient p) throws IOException {
		
		sendMsg("xxx");
	}

	protected void sendQueryLabMeasurementAck(LabMeasurement lm) throws IOException {
		
		sendMsg("xxx");
	}
	
	// message sent from patient and received by server
	
	
	
	protected void onLogin(String str) throws IOException {
		sendLoginAck();
	}
	
	protected void onLogout(String str) throws IOException {
		sendLogoutAck();		
	}
	
	protected void onHandleCaseAck(String str) {
		
	}
	
	protected void onSuspendCase(String str) {
		
	}
	
	protected void onFinalDiagnose(String str) {
		
	}
	
	protected void onQueryCase(String str) throws IOException {
		
		int caseID = 0;
		
		Case c = casem.findCaseByID(caseID);
		
		sendQueryCaseAck(c);
	}

	protected void onQueryChatHistory(String str) throws IOException {
		
		// request chat history from the database
		int patientID = 0;
		int doctorID = 0;
		List<Chat> chatHistory = chatm.findChatHistory(patientID, doctorID);

		// serialize chat history and send ack

		sendQueryChatHistoryAck(chatHistory);
	}
	
	protected void onChatMessage(String str) throws IOException {
		
		int patientID = 0;
		String content = str.substring(3);

		ServerPatientConnection spc = sccm.findServerPatientConnection(patientID);
		try {
			spc.forwardChatMessage(); // to doctor
		} catch (IOException e) {
			// TODO:
		}
	}
	
	protected void onQueryPatientProfile(String str) throws IOException {
		
		int patientID = 0;
		
		Patient p = pm.findPatientByID(patientID);
		
		sendQueryPatientProfileAck(p);
	}

	protected void onQueryLabMeasurement(String str) throws IOException {
		
		int lmID = 0;
		LabMeasurement m = labm.findLabMeasurementByID(lmID);
		
		sendQueryLabMeasurementAck(m);
	}
	
	
	
	
	
}
