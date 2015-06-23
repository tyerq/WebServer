/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import domain.Note;

public class NotesRepositoryInMemory implements NotesRepository {

	private static List<Note> notes;

	static {
		if (notes == null)
			notes = new ArrayList<Note>();
	}

	@Override
	public void saveNote(Note note) {
		notes.add(note);
	}

	@Override
	public List<Note> getAllNotes() {
		return notes;
	}

	@Override
	public Note getNote(String id) throws NoSuchElementException {
		for (Note note : notes)
			if (id.equals(note.getId()))
				return note;

		throw new NoSuchElementException();
	}

	@Override
	public void updateNote(Note note) throws NoSuchElementException {
		for (int i=0; i<notes.size(); i++)
			if (note.getId().equals(notes.get(i).getId())){
				if(note.getTitle()==null)
					note.setTitle(notes.get(i).getTitle());
				if(note.getCreated()==null)
					note.setCreated(notes.get(i).getCreated());
				if(note.getEdited()==null)
					note.setEdited(notes.get(i).getEdited());
				notes.set(i, note);
				if(note.getText()==null)
					note.setText(notes.get(i).getText());
				notes.set(i, note);
			}

		throw new NoSuchElementException();
	}

	@Override
	public void deleteNote(String id) {
		for (int i=0; i<notes.size(); i++)
			if (id.equals(notes.get(i).getId()))
				notes.remove(i);
				
	}
}
