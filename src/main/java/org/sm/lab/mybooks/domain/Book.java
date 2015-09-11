package org.sm.lab.mybooks.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.sm.lab.mybooks.enums.Genre;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    private String id;

    @NotNull
    @Size(min = 2)
    private String title;

    private String author;

    @DateTimeFormat(style = "M-")
    private Date startReadingDate;

    @DateTimeFormat(style = "M-")
    private Date endReadingDate;

    private Long rating;

    private Genre genre;
    
    private String review;

    @DBRef
    private Reader reader;

    private List<Note> notes = new ArrayList<Note>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getStartReadingDate() {
		return startReadingDate;
	}

	public void setStartReadingDate(Date startReadingDate) {
		this.startReadingDate = startReadingDate;
	}

	public Date getEndReadingDate() {
		return endReadingDate;
	}

	public void setEndReadingDate(Date endReadingDate) {
		this.endReadingDate = endReadingDate;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	public void copyFields(Book fromBook) {
		this.id = fromBook.getId();
		this.title = fromBook.getTitle();
		this.author = fromBook.getAuthor();
		this.startReadingDate = fromBook.getStartReadingDate();
		this.endReadingDate = fromBook.getEndReadingDate();
		this.rating = fromBook.getRating();
		this.genre = fromBook.getGenre();
		this.review = fromBook.getReview();
	}
    
	
}