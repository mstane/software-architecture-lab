package org.sm.lab.mybooks.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Note {

    /**
     */
    @NotNull
    @Size(min = 2)
    private String title;

    /**
     */
    private String content;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedTime;

    /**
     */
    @ManyToOne
    private Book book;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

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
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String getTitle() {
        return this.title;
    }

	public void setTitle(String title) {
        this.title = title;
    }

	public String getContent() {
        return this.content;
    }

	public void setContent(String content) {
        this.content = content;
    }

	public Date getCreatedTime() {
        return this.createdTime;
    }

	public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

	public Date getModifiedTime() {
        return this.modifiedTime;
    }

	public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

	public Book getBook() {
        return this.book;
    }

	public void setBook(Book book) {
        this.book = book;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
