package edu.asu.cse360.team25.server.connection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import edu.asu.cse360.team25.protocol.exception.ProtocolErrorException;
import edu.asu.cse360.team25.server.Case;
import edu.asu.cse360.team25.server.Chat;
import edu.asu.cse360.team25.server.Doctor;
import edu.asu.cse360.team25.server.Patient;
import edu.asu.cse360.team25.server.Server;

public class ServerPatientConnection extends ServerClientConnection {

	protected int state;

	protected int patientID;
	
	public ServerPatientConnection(Socket socket, Server server)
			throws IOException {

		super(socket, server);

	}

	@Override
	protected void dispatchReceivedMsg(String msg) throws IOException,
			ProtocolErrorException {

		// message format: <XXXX#content>,
		// where XXXX denotes the message type as readable string.

		int mark = msg.indexOf('#');
		String type = msg.substring(0, mark);
		String content = msg.substring(mark + 1);
		if (type.equals("Login"))
			onLogin(content);
		if (type.equals("Register"))
			onRegister(content);
		else if (type.equals("Logout"))
			onLogout(content);
		else if (type.equals("QueryDoctor"))
			onQueryDoctor(content);
		else if (type.equals("QueryCaseList"))
			onQueryCaseList(content);
		else if (type.equals("CreateCase"))
			onCreateCase(content);
		else if (type.equals("ResumeCase"))
			onResumeCase(content);
		else if (type.equals("QueryChatHistory"))
			onQueryChatHistory(content);
		else if (type.equals("ChatMessage"))
			onChatMessage(content);
		else if (type.equals("FinishCaseAck"))
			onFinishCaseAck(content);
		else if (type.equals("QueryPatientProfile"))
			onQueryPatientProfile(content);
		else if (type.equals("UpdatePatientProfile"))
			onUpdatePatientProfile(content);

	}

	// message sent from server and received by patient

	protected void sendLoginAck(boolean result) throws IOException {
		
		String content = result ? "OK!" : "Error!";
		
		sendMsg("LoginAck#" + content);
		
		System.out.println("LoginAck sent. content = <" + content + ">");
	}

	protected void sendRegisterAck(int id) throws IOException {
		
		String content = String.valueOf(id);
		
		sendMsg("RegisterAck#" + content);
		
		System.out.println("RegisterAck sent. content = <" + content + ">");
	}
	
	protected void sendLogoutAck() throws IOException {
		
		String content = "";
		
		sendMsg("LogoutAck#" + content);
		
		System.out.println("LogoutAck sent. content = <" + content + ">");
	}

	protected void sendQueryDoctorAck(List<Doctor> doctorList)
			throws IOException {

		StringBuffer sb = new StringBuffer();
		sb.append("QueryDoctorAck#");
		if (doctorList!= null && !doctorList.isEmpty()) {
			for (Doctor doctor : doctorList) {

				sb.append(doctor.toString());
				sb.append("#");
			}
			sb.deleteCharAt(sb.length() - 1);
		} else {
			sb.append("null");
		}

		sendMsg(sb.toString());
		
		System.out.println("QueryDoctorAck sent.");
	}

	protected void sendQueryCaseListAck(List<Case> caseList) throws IOException {

		StringBuffer sb = new StringBuffer();
		sb.append("QueryCaseListAck#");
		if (caseList != null && !caseList.isEmpty()) {
			for (Case c : caseList) {

				sb.append(c.toString());
				sb.append("#");
			}
			sb.deleteCharAt(sb.length() - 1);
		} else {
			sb.append("null");
		}

		sendMsg(sb.toString());
	}

	protected void sendCreateCaseAck() throws IOException {
		sendMsg("CreateCaseAck#");
	}

	protected void sendResumeCaseAck() throws IOException {
		sendMsg("ResumeCaseAck#");
	}

	protected void sendQueryChatHistoryAck(List<Chat> chats) throws IOException {
		sendMsg("QueryChatHistoryAck#");
	}

	protected void forwardChatMessage() throws IOException {

		sendMsg("ForwardChatMessage#");
	}

	protected void sendSuspendCase() throws IOException {
		sendMsg("SuspendCase#");
	}

	protected void sendFinishCase() throws IOException {
		sendMsg("FinishCase#");
	}

	protected void sendFinalDiagnoseAck() throws IOException {
		sendMsg("FinalDiagnoseAck#");
	}

	protected void sendQueryPatientProfileAck(Patient p) throws IOException {
	
		String content = "";
		
		if(p != null) {
			content = p.toString();
		} else {
			content = "null";
		}
		
		sendMsg("QueryPatientProfileAck#" + content);
		
		System.out.println("QueryPatientProfileAck sent. content = <" + content + ">");
		
	}

	protected void sendUpdatePatientProfileAck(boolean result) throws IOException {
		
		String content = "";
		
		if(result) {
			content = "OK!";
		} else {
			content = "Error!";
		}
		
		sendMsg("UpdatePatientProfileAck#" + content);
		
		System.out.println("UpdatePatientProfileAck sent. content = <" + content + ">");
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// message sent from patient and received by server

	protected void onLogin(String content) throws IOException,
			ProtocolErrorException {

		System.out.println("Login received. content = <" + content + ">");

		// content = <userID#password>
		String[] contents = content.split("#");
		if (contents.length != 2) {
			throw new ProtocolErrorException(
					"Invalid message content for <Login>!!!");
		}

		// if id and password match, accept
		// otherwise reject
		int id = 0;
		try {
			id = Integer.parseInt(contents[0]);
		} catch (NumberFormatException e) {
			throw new ProtocolErrorException("Invalid patient ID in <Login>!!! patient ID = " + contents[0]);
		}
		String password = contents[1];

		sendLoginAck(pm.checkPatientLoginReuest(id, password));

		
		pm.markPatientLogin(id);
		patientID = id;
	}

	protected void onRegister(String content) throws IOException, ProtocolErrorException {
		
		String[] contents = content.split("#");
		if (contents.length != 6) {
			throw new ProtocolErrorException(
					"Invalid message content for <Register>!!! content = " + content);
		}
		
		int id = pm.registerPatient(contents[0], contents[1], contents[2], contents[3], contents[4], contents[5]);
		
		
		sendRegisterAck(id);
		
	}
	
	protected void onLogout(String content) throws IOException, ProtocolErrorException {
		
		int id = 0;
		try {
			id = Integer.parseInt(content);
		} catch (NumberFormatException e) {
			throw new ProtocolErrorException("Invalid patient ID in <Logout>!!! patient ID = " + content);
		}
		
		sendLogoutAck();
		
		pm.markPatientLogout(id);
		patientID = -1;
	}

	protected void onQueryDoctor(String content) throws IOException,
			ProtocolErrorException {

		// content = <department#expertise>, note that both part can be "*"
		
		String[] contents = content.split("#");
		if (contents.length != 2) {
			throw new ProtocolErrorException(
					"Invalid message content for <QueryDoctor>!!! content = " + content);
		}

		List<Doctor> doctorlist = dm.listDoctors(contents[0], contents[1]);

		// serialize dlist, and send back to the patient

		sendQueryDoctorAck(doctorlist);

	}

	protected void onQueryCaseList(String content) throws IOException {

		// content = <>
		
		List<Case> caselist = casem.listCasesByPatientID(patientID);
		sendQueryCaseListAck(caselist);
	}

	protected void onCreateCase(String str) throws IOException {

		// z1 = first 0
		// z2 = second 0
		// name = patient's name
		int z1 = str.indexOf("0");
		int z2 = str.indexOf("0", z1 + 1);
		String name = str.substring(3, z1);

		int patientID = 0;
		int doctorID = 0;
		String painLevel = str.substring(z1 + 1, z2);
		String symptom = str.substring(z2 + 1);

		// then create case with the above information
		Case c = casem.createCase(patientID, doctorID, symptom, painLevel);

		ServerDoctorConnection sdc = sccm.findServerDoctorConnection(doctorID);

		sdc.sendHandleCase(c); // to doctor
	}

	protected void onResumeCase(String str) throws IOException {

		int caseID = Integer.parseInt(str.substring(3));

		// then resume case with the above information
		Case c = casem.findCaseByID(caseID);

		ServerDoctorConnection sdc = sccm.findServerDoctorConnection(c
				.getDoctorID());

		sdc.sendHandleCase(c); // to doctor
	}

	protected void onHandleCaseAck(String content) throws IOException {
		// if the case does not exist send create case ack
		// otherwise send resume case ack

		int caseID = 0;
		Case c = casem.findCaseByID(caseID);

		if (c.getState() == 0) {
			sendCreateCaseAck();
		} else {
			sendResumeCaseAck();
		}
	}

	protected void onQueryChatHistory(String content) throws IOException {

		// request chat history from the database
		int patientID = 0;
		int doctorID = 0;
		List<Chat> chatHistory = chatm.findChatHistory(patientID, doctorID);

		// serialize chat history and send ack

		sendQueryChatHistoryAck(chatHistory);
	}

	protected void onChatMessage(String str) throws IOException {

		int doctorID = 0;
		String content = str.substring(3);

		ServerDoctorConnection sdc = sccm.findServerDoctorConnection(doctorID);
		try {
			sdc.forwardChatMessage(content); // to doctor
		} catch (IOException e) {
			// TODO:
		}
	}

	protected void onFinalDiagnose(String content) throws IOException {

		int caseID = 0;
		casem.addFinalDiagnose(caseID);
		sendFinishCase();
	}

	protected void onFinishCaseAck(String content) throws IOException {

		sendFinalDiagnoseAck();
	}

	protected void onQueryPatientProfile(String content) throws IOException, ProtocolErrorException {

		System.out.println("QueryPatientProfile received. content = <" + content + ">");

		// content = <>
		
		Patient p = pm.findPatientByID(patientID);
		
		sendQueryPatientProfileAck(p);
	}

	protected void onUpdatePatientProfile(String content) throws IOException, ProtocolErrorException {

		System.out.println("UpdatePatientProfile received. content = <" + content + ">");

		// content = <Name#Gender#Height#Weight#BloodType>
		String[] contents = content.split("#");
		if(contents.length != 5) {
			throw new ProtocolErrorException(
					"Invalid message content for <UpdatePatientProfile>!!!");
		}
		

		boolean result = pm.updatePatientProfile(patientID, contents[0], contents[1], contents[2], contents[3], contents[4]);
		
		sendUpdatePatientProfileAck(result);
	}

}
