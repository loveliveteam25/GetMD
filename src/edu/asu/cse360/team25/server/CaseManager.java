package edu.asu.cse360.team25.server;

import java.util.List;
import java.util.WeakHashMap;

public class CaseManager {

	WeakHashMap<Integer, Case> cacheByID = new WeakHashMap<Integer, Case>();
	WeakHashMap<Integer, List<Case>> cacheByPID = new WeakHashMap<Integer, List<Case>>();
	
	public List<Case> listCasesByPatientID(int patientID) {
		
		// list all the cases for the given patient denoted by the patientID.
		List<Case> caseList = cacheByPID.get(patientID);
		if(caseList != null) {
			return caseList;
		} else {
		
			// query DB if necessary
		
		
		
		
			return null;
		}
	}
	
	
	public Case createCase(int patientID, int doctorID, String symptom, String painLevel) {
		
//		Case c = new Case(0, painLevel, symptom, patientID, doctorID);
		
		// Insert to database
		
		return null;
	}
	
	public Case findCaseByID(int caseID) {
		
		
		
		return null;
	}
	
	public boolean addFinalDiagnose(int caseID) {
		
		return false;
	}
	
	public boolean suspendCase(int caseID) {
		
		return false;
	}
	
	public boolean linkRefCase(int caseID, int refID) {
		
		return false;
	}
	
	public boolean linkRefCases(int caseID, int [] refIDs) {
		
		return false;
	}

	public boolean linkRefLabMeasurement(int caseID, int refID) {
		
		return false;
	}
	
	public boolean linkRefLabMeasurements(int caseID, int [] refIDs) {
		
		return false;
	}

}























