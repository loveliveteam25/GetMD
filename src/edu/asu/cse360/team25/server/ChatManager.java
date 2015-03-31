package edu.asu.cse360.team25.server;

import java.util.List;
import java.util.WeakHashMap;

public class ChatManager {

	WeakHashMap<Integer, Chat> cacheByID = new WeakHashMap<Integer, Chat>();
	WeakHashMap<IDPair, Chat> cacheByIDPair = new WeakHashMap<IDPair, Chat>();

	public Chat findChatByID(int chatID) {

		return null;
	}

	public List<Chat> findChatHistory(int patientID, int doctorID) {

		return null;
	}

	public void logChat(boolean direction, int patientID, int doctorID,
			String content) {

	}

	static class IDPair {
		int patientID;
		int doctorID;

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof IDPair) {
				IDPair pair = (IDPair) obj;
				return (this.patientID == pair.patientID)
						&& (this.doctorID == pair.doctorID);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {

			long idMix = ((long) patientID << 32)
					| ((long) doctorID & 0x00000000FFFFFFFFL);
			return Long.valueOf(idMix).hashCode();
		}

	}
}
