package org.sm.lab.mybooks2.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.datanucleus.api.jpa.annotations.Extension;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(style = "M-")
    private Date createdTime;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date modifiedTime;

    /**
     */
    @ManyToOne
    private Book book;

	@Id
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

	@Version
    @Column(name = "version")
    private Long version;

	public String getId() {
        return this.id;
    }

	public void setId(String id) {
        this.id = id;
    }

	public Long getVersion() {
        return this.version;
    }

	public void setVersion(Long version) {
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
