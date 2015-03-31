package edu.asu.cse360.team25.server;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class DoctorManager {

	WeakHashMap<Integer, Doctor> cacheByID = new WeakHashMap<Integer, Doctor>();
	WeakHashMap<String, List<Doctor>> cacheByDnS = new WeakHashMap<String, List<Doctor>>();; // cached
																								// by
																								// department
																								// and
																								// specialty

	public Doctor registerDoctor(String name, String department,
			String expertise) {

		return null;
	}

	public List<Doctor> listDoctors(String department, String specialty) {

		List<Doctor> res = cacheByDnS.get(department + "+" + specialty);
		if (res != null) {
			return res;
		} else {

			// query the database

			return null;
		}
	}

	public Doctor findDoctorByID(int id) {

		return null;
	}

	public void setupDummyDoctor() {

		Doctor[] d = new Doctor[6];

		d[0] = new Doctor(0, "Sheep Feng", "GAME", "GTA5");
		d[1] = new Doctor(1, "Horse Feng", "GAME", "Naruto");
		d[2] = new Doctor(2, "Bull Feng", "GAME", "LoveLive");
		d[3] = new Doctor(3, "Camel Feng", "Cook", "meat ball");
		d[4] = new Doctor(4, "Mule Feng", "Cook", "yogurt");
		d[5] = new Doctor(5, "Elephant Feng", "Cook", "sandwith");

		for (int i = 0; i < d.length; i++) {

			//
			cacheByID.put(d[i].getDoctorID(), d[i]);
			//
			List<Doctor> list = cacheByDnS.get(d[i].getDepartment() + "+"
					+ d[i].getExpertise());
			if (list == null) {
				list = new ArrayList<Doctor>();
				list.add(d[i]);
				cacheByDnS.put(
						d[i].getDepartment() + "+" + d[i].getExpertise(), list);
			} else {
				list.add(d[i]);
			}

			//

			List<Doctor> listDstar = cacheByDnS.get("*+" + d[i].getExpertise());
			if (listDstar == null) {
				listDstar = new ArrayList<Doctor>();
				listDstar.add(d[i]);
				cacheByDnS.put("*+" + d[i].getExpertise(), listDstar);
			} else {
				listDstar.add(d[i]);
			}

			//
			List<Doctor> listSstar = cacheByDnS
					.get(d[i].getDepartment() + "+*");
			if (listSstar == null) {
				listSstar = new ArrayList<Doctor>();
				listSstar.add(d[i]);
				cacheByDnS.put(d[i].getDepartment() + "+*", listSstar);
			} else {
				listSstar.add(d[i]);
			}

			//
			List<Doctor> listStarStar = cacheByDnS.get("*+*");
			if (listStarStar == null) {
				listStarStar = new ArrayList<Doctor>();
				listStarStar.add(d[i]);
				cacheByDnS.put("*+*", listStarStar);
			} else {
				listStarStar.add(d[i]);
			}
		}

	}

}
