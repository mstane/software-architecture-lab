package org.sm.lab.mybooks.server.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.Genre;
import org.sm.lab.mybooks.shared.dto.NoteDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

@Entity
public class Book implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@NotNull
	@Size(min = 2)
	private String title;
	
    private String author;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_reading_date")
    private Date startReadingDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_reading_date")
    private Date endReadingDate;
    
    private Long rating;

    @Enumerated(EnumType.STRING)
    private Genre genre;
    
    @Lob
    private String review;
    
    @ManyToOne
    @JoinColumn(name="reader_id", referencedColumnName="id")
    private Reader reader;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy="book")
    private List<Note> notes = new ArrayList<Note>(0);
    

    
    public Book() {
    }

    public Book(BookDto bookDto) {
    	setDto(bookDto);
    }
    
    public void setDto(BookDto bookDto) {
        this.id = bookDto.getId();
        this.title = bookDto.getTitle();
        this.author = bookDto.getAuthor();
        this.startReadingDate = bookDto.getStartReadingDate();
        this.endReadingDate = bookDto.getEndReadingDate();
        this.rating = bookDto.getRating();
        this.genre = bookDto.getGenre();
        this.review = bookDto.getReview();
    }
    
    public BookDto getDto() {
    	List<NoteDto> noteDtoList = new ArrayList<NoteDto>();
    	for (Note note : notes) {
    		noteDtoList.add(new NoteDto(note.getId(), note.getTitle()));
		}
    	
    	ReaderDto readerDto = new ReaderDto();
    	readerDto.setId(readerDto.getId());
    	readerDto.setUsername(readerDto.getUsername());
    	
    	BookDto bookDto = new BookDto(this.getId(), this.getTitle(), this.getAuthor(), this.getStartReadingDate(), this.getEndReadingDate(), this.getRating(), this.genre, this.review, readerDto, noteDtoList);
    	return bookDto;
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


  }

