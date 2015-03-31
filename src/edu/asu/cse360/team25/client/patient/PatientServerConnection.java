package edu.asu.cse360.team25.client.patient;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import edu.asu.cse360.team25.client.ClientServerConnection;
import edu.asu.cse360.team25.protocol.DoctorInfo;
import edu.asu.cse360.team25.protocol.PatientInfo;
import edu.asu.cse360.team25.protocol.exception.InvalidDataRecordException;
import edu.asu.cse360.team25.protocol.exception.ProtocolErrorException;

public class PatientServerConnection extends ClientServerConnection {

	protected static final int patientListeningPort = 10230;
	protected static final String serverAddress = "localhost";

	PatientClient pc;
	
	PatientMainFrame pmf;
	
	protected int patientID;

	protected ConnectionState state;

	protected enum ConnectionState {
		UNCONNECTED, CONNECTED, ONLINE, ON_CASE
	}

	// message sent from patient and received by server

	public PatientServerConnection(PatientClient pc) throws IOException {

		super(serverAddress, patientListeningPort);

		this.pc = pc;
	}
	
	public void setPatientMainFrame(PatientMainFrame pmf) {
		
		this.pmf = pmf;

	}
	
	@Override
	protected void dispatchReceivedMsg(String msg) throws IOException {

		// message format: <XXXX#content>,
		// where XXXX denotes the message type as readable string.

		int mark = msg.indexOf('#');
		String type = msg.substring(0, mark);
		String content = msg.substring(mark + 1);
		if (type.equals("LoginAck"))
			onLoginAck(content);
		else if (type.equals("RegisterAck"))
			onRegisterAck(content);
		else if (type.equals("LogoutAck"))
			onLogoutAck(content);
		else if (type.equals("QueryDoctorAck"))
			onQueryDoctorAck(content);
		else if (type.equals("QueryCaseListAck"))
			onQueryCaseListAck(content);
		else if (type.equals("CreateCaseAck"))
			onCreateCaseAck(content);
		else if (type.equals("ResumeCaseAck"))
			onResumeCaseAck(content);
		else if (type.equals("QueryChatHistoryAck"))
			onQueryChatHistoryAck(content);
		else if (type.equals("ChatMessage"))
			onChatMessage(content);
		else if (type.equals("FinishCase"))
			onFinishCase(content);
		else if (type.equals("QueryPatientProfileAck"))
			onQueryPatientProfileAck(content);
		else if (type.equals("UpdatePatientProfileAck"))
			onUpdatePatientProfileAck(content);

	}

	
	protected void sendRegister(String password, String name, String gender, String height, String weight, String birthday) throws IOException {
		
		
		sendMsg("Register#" + password + "#" + name + "#" + gender + "#" + height + "#" + weight + "#" + birthday);
		
		System.out.println("Register sent.");

	}

	protected void sendLogin(int id, String password) throws IOException,
			ProtocolErrorException {


		patientID = id;

		// <Login#PatientID#Password>

		sendMsg("Login#" + id + "#" + password);
		
		System.out.println("Login sent.");
	}

	protected void sendLogout() throws IOException {

		// <Logout#PatientID>

		sendMsg("Logout#" + patientID);
	}

	protected void sendQueryDoctor(String department, String expertise)
			throws IOException, ProtocolErrorException {

		if (department.contains("#") || expertise.contains("#")) {

			throw new ProtocolErrorException(
					"Invalid department string or expertise string!!!");
		}

		// <QueryDoctor#department#expertise>, note that both part can be "*"

		sendMsg("QueryDoctor#" + department + "#" + department);
	}

	protected void sendQueryCaseList() throws IOException {

		// <QueryCaseList#>

		sendMsg("QueryCaseList#");
	}

	protected void sendCreateCase(String name, String painLevel, String symptom)
			throws IOException {
		// message = type + name + 0 + painlevel + 0 + symptom
		// note that painlevel and symptom do not contain any integer
		String type = "CreateCase#";
		String z1 = String.valueOf(0);
		String z2 = String.valueOf(0);

		String msg = type + name + z1 + painLevel + z2 + symptom;
		sendMsg(msg);
	}

	protected void sendResumeCase(String caseID) throws IOException {
		String type = "ResumeCase#";
		String msg = type + caseID;
		sendMsg(msg);
	}

	protected void sendQueryChatHistory() throws IOException {
		sendMsg("QueryChatHistory#");
	}

	protected void sendChatMessage(String content) throws IOException {
		String type = "ChatMessage#";
		String msg = type + content;
		sendMsg(msg);
	}

	protected void sendFinishCaseAck() throws IOException {
		sendMsg("FinishCaseAck#");
	}

	protected void sendQueryPatientProfile() throws IOException {

		// <QueryPatientProfile#>

		sendMsg("QueryPatientProfile#");
	}

	protected void sendUpdatePatientProfile(String name, String gender,
			String height, String weight, String bloodType) throws IOException {

		// TODO: sanity check

		// <UpdatePatientProfile#Name#Gender#Height#Weight#BloodType>

		sendMsg("UpdatePatientProfile#" + name + "#" + gender + "#" + height
				+ "#" + weight + "#" + bloodType);
	}

	// message sent from server and received by patient

	protected void onLoginAck(String content) {

		if(content.equals("OK!"))
			pc.setLoginAck(true);
		else
			pc.setLoginAck(false);

		synchronized(pc) {
			pc.notify();
		}
		
		
		// enter the main page

		System.out.println("LoginAck received.");
	}

	protected void onRegisterAck(String content) {

		// quit

		pc.patientID = Integer.parseInt(content);
		
		synchronized(pc) {
			pc.notify();
		}

		System.out.println("RegisterAck received.");
	}

	protected void onLogoutAck(String content) {

		// quit

		System.out.println("LogoutAck received.");
	}

	protected void onQueryDoctorAck(String content) {

		
		
		String[] contents = content.split("#");

		List<DoctorInfo> docList = new ArrayList<DoctorInfo>();

		try {

			for (int i = 0; i < contents.length; i++) {
				DoctorInfo di = new DoctorInfo(contents[i]);
				docList.add(di);
			}

		} catch (InvalidDataRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// display the list of doctor
		
		System.out.println("QueryDoctorAck received.");
		for(DoctorInfo di : docList) {
			System.out.println("\t" + di);
		}
		
	}

	protected void onQueryCaseListAck(String content) {

		// display case list
	}

	protected void onCreateCaseAck(String content) {

		// display case created
	}

	protected void onResumeCaseAck(String content) {

		// display case resumed
	}

	protected void onQueryChatHistoryAck(String content) {

		// display chat history
	}

	protected void onChatMessage(String content) {

		// display the chat content
	}

	protected void onSuspendCase(String content) {

		// Temporary quit
	}

	protected void onFinishCase(String content) throws IOException {

		sendFinishCaseAck();
	}

	protected void onQueryPatientProfileAck(String content) {

		// display patient profile

		System.out.println("QueryPatientProfileAck received. content = <" + content + ">");

		try {
			PatientInfo pi = new PatientInfo(content);
			
			pc.setPatientInfo(pi);
			
			System.out.println("\t" + pi.toString());
			
			synchronized(pc) {
				pc.notify();
			}

			
		} catch (InvalidDataRecordException e) {
			e.printStackTrace();
		}
		
	}

	protected void onUpdatePatientProfileAck(String content) {

		System.out.println("UpdatePatientProfileAck received. content = <" + content + ">");
		System.out.println("\t" + content);
		
		if(content.equals("OK!")) {
			pmf.updatePatientInfo(true);
		} else {
			pmf.updatePatientInfo(false);
		}
		
	}


}
