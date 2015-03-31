package edu.asu.cse360.team25.server;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class PatientManager {

	protected int idNext = 7;
	
	protected WeakHashMap<Integer, Patient> cacheByID = new WeakHashMap<Integer, Patient>();
	protected WeakHashMap<String, List<Patient>> cacheByName = new WeakHashMap<String, List<Patient>>();

	public Patient findPatientByID(int id) {

		Patient p = cacheByID.get(id);
		if(p != null) {
			return p;
		} else {
		
			// TODO: query the database
			
			return null;
		}
	}

	public List<Patient> findPatientByName(String name) {
		
		return null;
	}
	
	public boolean checkPatientLoginReuest(int id, String password) {

		if(cacheByID.containsKey(id)) {
			Patient p = cacheByID.get(id);
			return p.getPassword().equals(password);
		} else {
			
			// TODO: query database
			return false;
		}
		
	}

	public int registerPatient(String password, String name, String gender,
			String height, String weight, String birthday) {

		int id = getNextID();
		
		Patient p = new Patient(id, password, name, gender, height, weight, birthday);
		
		updateCache(p);
		
		return id;
	}

	public boolean updatePatientProfile(int patientID, String name, String gender,
			String height, String weight, String birthday) {
		
		Patient p = cacheByID.get(patientID);
		if(p != null) {
			p.setName(name);
			p.setGender(gender);
			p.setHeight(height);
			p.setWeight(weight);
			p.setBirthday(birthday);
		}
		
		// TODO: update database record
		
		return true;
	}
	
	public boolean updatePassword(int patientID, String pwOld, String pwNew) {
		
		// TODO:
		
		return false;
	}
	
	public boolean markPatientLogin(int patientID) {
		
		// TODO:
		
		return false;
	}
	
	public boolean markPatientLogout(int patientID) {

		// TODO:
		
		return false;
	}
	
	
	public void setupDummyPatient() {
		
		Patient[] p = new Patient[7];
		
		p[0] = new Patient(0, "123456", "Tomato Lin", "Female", "158", "49", "Unkown");
		p[1] = new Patient(1, "123456", "Potato Lin", "Female", "159", "48", "Unkown");
		p[2] = new Patient(2, "123456", "Onion Lin", "Female", "160", "47", "Unkown");
		p[3] = new Patient(3, "123456", "Banana Lin", "Female", "161", "46", "Unkown");
		p[4] = new Patient(4, "123456", "Apple Lin", "Female", "162", "45", "Unkown");
		p[5] = new Patient(5, "123456", "Orange Lin", "Female", "163", "44", "Unkown");
		p[6] = new Patient(6, "123456", "Lemmon Lin", "Female", "164", "43", "Unkown");
		
		for(int i = 0; i < p.length; i++) {
			updateCache(p[i]);
		}
		
	}
	
	protected synchronized void updateCache(Patient p) {
		
		cacheByID.put(p.getPatientID(), p);
		List<Patient> list = cacheByName.get(p.getName());
		if(list == null) {
			list = new ArrayList<Patient>();
			list.add(p);
			cacheByName.put(p.getName(), list);
		} else {
			list.add(p);
		}

	}
	
	protected synchronized int getNextID() {
		
		return idNext++;
	}
	
}
