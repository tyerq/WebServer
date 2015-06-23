package service;

import domain.Note;

import java.net.UnknownHostException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import repository.NotesRepository;
import repository.NotesRepositoryInMemory;
import repository.NotesRepositoryMongoDB;

public class NotesServiceImpl implements NotesService {

	private static NotesRepository notesRepository;

	public NotesServiceImpl() {
		if (notesRepository == null)
			notesRepository = new NotesRepositoryMongoDB();
	}

	@Override
	public void addNote(Note note) {
		if (note == null || note.getText() == null || note.getTitle() == null) {
			return;
		}
		notesRepository.saveNote(note);
	}

	@Override
	public void addNote(String title, String note) {
		addNote(new Note(title, note));
	}

	@Override
	public List<Note> getAllNotes() {
		return notesRepository.getAllNotes();
	}

	@Override
	public Note getNote(String id) {
		try {
			return notesRepository.getNote(id);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	@Override
	public void editNote(Note note) {
		try {
			notesRepository.updateNote(note);
		} catch (NoSuchElementException e) {
			return;
		}
	}

	@Override
	public void deleteNote(String id) {
		notesRepository.deleteNote(id);
	}

}
