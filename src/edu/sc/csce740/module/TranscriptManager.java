package edu.sc.csce740.module;

import edu.sc.csce740.exception.*;
import edu.sc.csce740.model.*;

/**
 * 
 * Transcript manager module.
 *
 */
public class TranscriptManager {
	/**
	 * 
	 * @param userId
	 * @param transcript
	 * @param permanent
	 * @throws StudentRecordNotFoundException
	 * @throws StudentRecordsNotSavedException
	 */
	public static void updateTranscript(String userId, StudentRecord transcript, boolean permanent) throws StudentRecordNotFoundException, StudentRecordsNotSavedException {
		DataStore.updateTranscript(userId, transcript, permanent);
	}
	
	/**
	 * 
	 * @param userId
	 * @param note
	 * @param permanent
	 * @throws StudentRecordNotFoundException
	 * @throws StudentRecordsNotSavedException
	 */
	public static void addNote(String userId, String note, boolean permanent) throws StudentRecordNotFoundException, StudentRecordsNotSavedException {
		DataStore.addNote(userId, note, permanent);
	}
	
	/**
	 * 
	 * @param userId
	 * @return transcript
	 * @throws StudentRecordNotFoundException
	 */
	public static StudentRecord getTranscript(String userId) throws StudentRecordNotFoundException {
		StudentRecord transcript = DataStore.getTranscript(userId);
		if (transcript == null) {
			throw new StudentRecordNotFoundException();
		} //else
		
		return transcript;
	}
}
