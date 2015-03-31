package edu.asu.cse360.team25.server;

public class LabMeasurement {

	protected int labMeasurementID;
	
	protected String content;
	
	protected int patientID;
	protected int nureseID;
	
	
	
	public LabMeasurement(int labMeasurementID, String content, int patientID,
			int nureseID) {
		super();
		this.labMeasurementID = labMeasurementID;
		this.content = content;
		this.patientID = patientID;
		this.nureseID = nureseID;
	}

	public int getLabMeasurementID() {
		return labMeasurementID;
	}

	public String getContent() {
		return content;
	}

	public int getPatientID() {
		return patientID;
	}

	public int getNureseID() {
		return nureseID;
	}
	
	
	
	
}
