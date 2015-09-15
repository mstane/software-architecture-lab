package org.sm.lab.mybooks.domain;
import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table
public class Note implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@PrimaryKey
    private Long id;

    @NotNull
    @Size(min = 2)
    private String title;

    private String content;

    @Column("created_time")
    private Date createdTime;

    @Column("modified_time")
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
