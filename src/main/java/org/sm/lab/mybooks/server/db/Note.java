
package org.sm.lab.mybooks.server.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.NoteDto;

@Entity
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    private String title;
    
    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time")
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "modified_time")
    private Date modifiedTime;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public Note() {
    }

    public Note(NoteDto noteDto) {
        setDto(noteDto);
    }
    
    public void setDto(NoteDto noteDto) {
        this.id = noteDto.getId();
        this.title = noteDto.getTitle();
        this.content = noteDto.getContent();
        this.createdTime = noteDto.getCreatedTime();
        this.modifiedTime = noteDto.getModifiedTime();
    }

    public NoteDto getDto() {
        NoteDto noteDto = new NoteDto(this.id, this.title, this.content, this.createdTime, this.modifiedTime, null);

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        noteDto.setBook(bookDto);

        return noteDto;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
