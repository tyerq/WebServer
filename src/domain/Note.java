package domain;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Note {
	String id;
	
	String title;
	String created;
	String edited;
	String text;

	public Note(String id) {
		this.id = id;
	}

	public Note(String title, String text) {
		this.title = title;
		this.created = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy")
				.format(new Date());
		this.text = text;
		this.id = String.valueOf(Math.random()*Math.random()+Math.random());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getEdited() {
		return edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}
}