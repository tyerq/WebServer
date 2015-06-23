package repository;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import domain.Note;

public class NotesRepositoryMongoDB implements NotesRepository {

	MongoCollection<Document> collection;

	public NotesRepositoryMongoDB() {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("notesdb");

		collection = database.getCollection("notes");
	}

	@Override
	public void saveNote(Note note) {
		Document doc = new Document("id", note.getId())
				.append("title", note.getTitle())
				.append("text", note.getText())
				.append("created", note.getCreated())
				.append("edited", note.getEdited());

		collection.insertOne(doc);
	}

	@Override
	public List<Note> getAllNotes() {
		MongoCursor<Document> cursor = collection.find().iterator();
		List<Note> res = new ArrayList<Note>();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				Note note = new Note(doc.getString("id"));
				note.setTitle(doc.getString("title"));
				note.setText(doc.getString("text"));
				note.setCreated(doc.getString("created"));
				note.setEdited(doc.getString("edited"));
				res.add(note);
			}
		} finally {
			cursor.close();
		}
		return res;
	}

	@Override
	public Note getNote(String id) throws NoSuchElementException {
		Document doc = collection.find(eq("id", id)).first();
		if (doc == null)
			throw new NoSuchElementException();
		Note note = new Note(doc.getString("id"));
		note.setTitle(doc.getString("title"));
		note.setText(doc.getString("text"));
		note.setCreated(doc.getString("created"));
		note.setEdited(doc.getString("edited"));
		return note;
	}

	@Override
	public void updateNote(Note note) throws NoSuchElementException {

		collection.updateOne(
				eq("id", note.getId()),
				new Document("$set", new Document("title", note.getTitle())
						.append("edited", note.getEdited()).append("text",
								note.getText())));
	}

	@Override
	public void deleteNote(String id) {
		collection.deleteOne(eq("id", id));
	}
}
