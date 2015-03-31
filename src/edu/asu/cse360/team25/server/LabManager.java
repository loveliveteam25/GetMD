package edu.asu.cse360.team25.server;

import java.util.List;
import java.util.WeakHashMap;

public class LabManager {

	WeakHashMap<Integer, LabMeasurement> cacheByID = new WeakHashMap<Integer, LabMeasurement>();

	
	public LabMeasurement findLabMeasurementByID(int id) {
		
		
		return null;
	}

	public List<LabMeasurement> findAllLabMeasurementOfGivenPatient(int id) {
		
		
		return null;
	}

	public void addLabMeasurement(int id, String content, int patientID,
			int nureseID) {
		
	}
}
