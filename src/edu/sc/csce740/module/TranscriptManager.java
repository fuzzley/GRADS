package edu.sc.csce740.module;

import edu.sc.csce740.exception.*;
import edu.sc.csce740.model.*;


public class TranscriptManager {
	
	public static void updateTranscript(String userId, StudentRecord transcript, boolean permanent) throws StudentRecordNotFoundException, StudentRecordsNotSavedException {
		DataStore.updateTranscript(userId, transcript, permanent);
	}

	public static void addNote(String userId, String note, boolean permanent) throws StudentRecordNotFoundException, StudentRecordsNotSavedException {
		DataStore.addNote(userId, note, permanent);
	}

	public static StudentRecord getTranscript(String userId) throws StudentRecordNotFoundException {
		StudentRecord transcript = DataStore.getTranscript(userId);
		if (transcript == null) {
			throw new StudentRecordNotFoundException();
		} //else
		
		return transcript;
	}
}
