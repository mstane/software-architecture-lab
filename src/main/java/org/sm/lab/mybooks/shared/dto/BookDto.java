package org.sm.lab.mybooks.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(min = 2, message = "{name_size_validation_message}")
    private String title;
    
    private String author;
    private Date startReadingDate;
    private Date endReadingDate;
    private Long rating;
    private Genre genre;
    private String review;
    private ReaderDto reader;
    private List<NoteDto> notes = new ArrayList<NoteDto>(0);
    
    
    public BookDto() {
    	
    }
    
    public BookDto(Long id, String title) {
    	this.id = id;
    	this.title = title;
    }

	public BookDto(Long id, String title, String author, Date startReadingDate,
			Date endReadingDate, Long rating, Genre genre, String review,
			ReaderDto reader, List<NoteDto> notes) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.startReadingDate = startReadingDate;
		this.endReadingDate = endReadingDate;
		this.rating = rating;
		this.genre = genre;
		this.review = review;
		this.reader = reader;
		this.notes = notes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public ReaderDto getReader() {
		return reader;
	}

	public void setReader(ReaderDto reader) {
		this.reader = reader;
	}

	public List<NoteDto> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteDto> notes) {
		this.notes = notes;
	}

    
    
    
  }

