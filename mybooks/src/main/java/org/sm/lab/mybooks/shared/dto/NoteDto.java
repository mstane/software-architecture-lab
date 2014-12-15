
package org.sm.lab.mybooks.shared.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(min = 4, message = "{name_size_validation_message}")
    private String title;

    @NotNull
    @Size(min = 4, message = "{username_size_validation_message}")
    private String content;

    private Date createdTime;
    private Date modifiedTime;

    private BookDto book;

    public NoteDto() {

    }

    public NoteDto(Long id, String name) {
        this.id = id;
        this.title = name;
    }

    public NoteDto(Long id, String title, String content, Date createdTime, Date modifiedTime, BookDto book) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
        this.book = book;
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

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

}
