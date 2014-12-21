package org.sm.lab.mybooks2.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.ManyToOne;

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

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
