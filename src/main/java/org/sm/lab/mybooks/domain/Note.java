package org.sm.lab.mybooks.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 2)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;

    @JsonIgnore
    @ManyToOne
    private Book book;

    
    
	@PrePersist
	protected void onCreate() {
		createdTime = new Date();
		modifiedTime = new Date();
	}

	@PreUpdate
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
