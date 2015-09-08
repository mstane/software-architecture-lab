package org.sm.lab.mybooks3.domain;
import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Note implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    private String id;

    @NotNull
    @Size(min = 2)
    private String title;

    private String content;

    private Date createdTime;

    private Date modifiedTime;

    @JsonIgnore
    private Book book;
    
	protected void onCreate() {
		createdTime = new Date();
		modifiedTime = new Date();
	}

	protected void onUpdate() {
		modifiedTime = new Date();
	}

	
	
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
