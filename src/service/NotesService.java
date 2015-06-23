package service;

import domain.Note;

import java.util.List;

public interface NotesService {
    public void addNote(Note note);
    public void addNote(String title, String note);
    public List<Note> getAllNotes();
    public Note getNote(String id);
    public void editNote(Note note);
    public void deleteNote(String id);
}
