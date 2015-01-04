package org.sm.lab.mybooks2.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.datanucleus.api.jpa.annotations.Extension;
import org.sm.lab.mybooks2.enums.Genre;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
public class Book {

    /**
     */
    @NotNull
    @Size(min = 2)
    private String title;

    /**
     */
    private String author;

    /**
     */
    private String url;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startReadingDate;

    /**
     */
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endReadingDate;

    /**
     */
    private Long rating;

    /**
     */
    @Enumerated(EnumType.STRING)
    private Genre genre;

    /**
     */
    @ManyToOne
    private Reader reader;

    /**
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<Note> notes = new ArrayList<Note>();

	@PersistenceContext
    transient EntityManager entityManager;

	public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("title", "author", "url", "startReadingDate", "endReadingDate", "rating", "genre", "reader", "notes");

	public static final EntityManager entityManager() {
        EntityManager em = new Book().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	@Transactional
    public static long countBooks() {
        return findAllBooks().size();
    }

	@Transactional
    public static List<Book> findAllBooks() {
        return entityManager().createQuery("SELECT o FROM Book o", Book.class).getResultList();
    }

	@Transactional
    public static List<Book> findAllBooks(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Book o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Book.class).getResultList();
    }

	@Transactional
    public static Book findBook(String id) {
        if (id == null || id.length() == 0) return null;
        return entityManager().find(Book.class, id);
    }

	@Transactional
    public static List<Book> findBookEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Book o", Book.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public static List<Book> findBookEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Book o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Book.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Book attached = Book.findBook(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Book merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Book merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

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

	public static Long countFindBooksByTitle(String title) {
        if (title == null || title.length() == 0) throw new IllegalArgumentException("The title argument is required");
        EntityManager em = Book.entityManager();
        TypedQuery q = em.createQuery("SELECT COUNT(o) FROM Book AS o WHERE o.title = :title", Long.class);
        q.setParameter("title", title);
        return ((Long) q.getSingleResult());
    }

	public static TypedQuery<Book> findBooksByTitle(String title) {
        if (title == null || title.length() == 0) throw new IllegalArgumentException("The title argument is required");
        EntityManager em = Book.entityManager();
        TypedQuery<Book> q = em.createQuery("SELECT o FROM Book AS o WHERE o.title = :title", Book.class);
        q.setParameter("title", title);
        return q;
    }

	public static TypedQuery<Book> findBooksByTitle(String title, String sortFieldName, String sortOrder) {
        if (title == null || title.length() == 0) throw new IllegalArgumentException("The title argument is required");
        EntityManager em = Book.entityManager();
        StringBuilder queryBuilder = new StringBuilder("SELECT o FROM Book AS o WHERE o.title = :title");
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            queryBuilder.append(" ORDER BY ").append(sortFieldName);
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                queryBuilder.append(" ").append(sortOrder);
            }
        }
        TypedQuery<Book> q = em.createQuery(queryBuilder.toString(), Book.class);
        q.setParameter("title", title);
        return q;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getTitle() {
        return this.title;
    }

	public void setTitle(String title) {
        this.title = title;
    }

	public String getAuthor() {
        return this.author;
    }

	public void setAuthor(String author) {
        this.author = author;
    }

	public String getUrl() {
        return this.url;
    }

	public void setUrl(String url) {
        this.url = url;
    }

	public Date getStartReadingDate() {
        return this.startReadingDate;
    }

	public void setStartReadingDate(Date startReadingDate) {
        this.startReadingDate = startReadingDate;
    }

	public Date getEndReadingDate() {
        return this.endReadingDate;
    }

	public void setEndReadingDate(Date endReadingDate) {
        this.endReadingDate = endReadingDate;
    }

	public Long getRating() {
        return this.rating;
    }

	public void setRating(Long rating) {
        this.rating = rating;
    }

	public Genre getGenre() {
        return this.genre;
    }

	public void setGenre(Genre genre) {
        this.genre = genre;
    }

	public Reader getReader() {
        return this.reader;
    }

	public void setReader(Reader reader) {
        this.reader = reader;
    }

	public List<Note> getNotes() {
        return this.notes;
    }

	public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
