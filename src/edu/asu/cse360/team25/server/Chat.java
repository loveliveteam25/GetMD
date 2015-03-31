package edu.asu.cse360.team25.server;

public class Chat {

	protected int chatID;
	
	protected int caseID;
	
	protected boolean direction;
	
	protected int patientID;
	protected int doctorID;
	
	protected String message;

	
	
	public Chat(int chatID, int caseID, boolean direction, int patientID,
			int doctorID, String message) {
		super();
		this.chatID = chatID;
		this.caseID = caseID;
		this.direction = direction;
		this.patientID = patientID;
		this.doctorID = doctorID;
		this.message = message;
	}

	public int getChatID() {
		return chatID;
	}

	public int getCaseID() {
		return caseID;
	}

	public boolean isDirection() {
		return direction;
	}

	public int getPatientID() {
		return patientID;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public String getMessage() {
		return message;
	}

	
	
}
