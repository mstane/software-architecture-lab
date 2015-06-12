package org.sm.lab.mybooks3.domain;

public class SearchItem {
	
	private String title;
	private String link;
	private String shortContent;
	
	public SearchItem(Book book) {
		super();
		this.title = "Book: " + book.getTitle();
		this.link = "#/books/view/" + book.getId();
		this.shortContent = "Author: " + book.getAuthor() + "; Review: " + book.getReview();
	}

	public SearchItem(Note note) {
		super();
		this.title = "Note: " + note.getTitle();
		this.link = "#/notes/view/" + note.getId();
		this.shortContent = "For book: " + note.getBook().getTitle() + "; Content: " + note.getContent();
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getShortContent() {
		return shortContent;
	}
	
}
