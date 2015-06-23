package domain;

public class Post {
	String author;
	String post;

	public Post(String author, String post) {
		this.author = author;
		this.post = post;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
}
