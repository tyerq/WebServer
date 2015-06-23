/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.Note;

import java.util.List;
import java.util.NoSuchElementException;

public interface NotesRepository {
	
    public void saveNote(Note note);
    public List<Note> getAllNotes();
    public Note getNote(String id) throws NoSuchElementException;
    public void updateNote(Note note) throws NoSuchElementException;
    public void deleteNote(String id);
}
