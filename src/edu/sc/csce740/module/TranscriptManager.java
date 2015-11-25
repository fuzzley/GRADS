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
	 * Update a StudentRecord of an existing student.
	 * @param userId The id of the student to whom the student record belongs.
	 * @param transcript The new StudentRecord that will replace the old one.
	 * @param permanent Indicates whether the change is permanent or temporary.
	 * @throws StudentRecordNotFoundException
	 */
	public static void updateTranscript(String userId, StudentRecord transcript, boolean permanent) throws StudentRecordNotFoundException, StudentRecordsNotSavedException {
		DataStore.updateTranscript(userId, transcript, permanent);
	}
	
	/**
	 * Add a note to a student's record.
	 * @param userId The id of the student for whom the note is intended.
	 * @param note The note to be added to the student's record.
	 * @param permanent Indicates whether the change is permanent or temporary.
	 * @throws StudentRecordNotFoundException
	 */
	public static void addNote(String userId, String note, boolean permanent) throws StudentRecordNotFoundException, StudentRecordsNotSavedException {
		DataStore.addNote(userId, note, permanent);
	}
	
	/**
	 * Returns the StudentRecord object associated with the given user id.
	 * @param userId The user id to search for.
	 * @return Returns the StudentRecord that matches the given user id.
	 */
	public static StudentRecord getTranscript(String userId) throws StudentRecordNotFoundException {
		StudentRecord transcript = DataStore.getTranscript(userId);
		if (transcript == null) {
			throw new StudentRecordNotFoundException();
		} //else
		
		return transcript;
	}
}
