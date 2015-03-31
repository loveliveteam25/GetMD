package edu.asu.cse360.team25.server;

public class Case {

	protected int caseID;

	protected String painLevel;
	protected String symptom;

	protected int patientID;
	protected int doctorID;

	protected String refCases;
	protected String refLabMeasurements;

	protected int state;

	public Case(int caseID, String painLevel, String symptom, int patientID,
			int doctorID) {
		super();
		this.caseID = caseID;
		this.painLevel = painLevel;
		this.symptom = symptom;
		this.patientID = patientID;
		this.doctorID = doctorID;
	}

	public int getCaseID() {
		return caseID;
	}

	public String getPainLevel() {
		return painLevel;
	}

	public String getSymptom() {
		return symptom;
	}

	public int getPatientID() {
		return patientID;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public String getRefCases() {
		return refCases;
	}

	public void setRefCases(String refCases) {
		this.refCases = refCases;
	}

	public String getRefLabMeasurements() {
		return refLabMeasurements;
	}

	public void setRefLabMeasurements(String refLabMeasurements) {
		this.refLabMeasurements = refLabMeasurements;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return caseID + "," + painLevel + "," + symptom + "," + patientID + ","
				+ doctorID + "," + refCases + "," + refLabMeasurements + ","
				+ state;
	}

}
