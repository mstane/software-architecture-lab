package org.sm.lab.mybooks.server;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.db.Book;
import org.sm.lab.mybooks.server.db.Note;
import org.sm.lab.mybooks.server.db.Reader;
import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.NoteDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;
import org.sm.lab.mybooks.shared.exception.MyBooksException;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


public class AppDatabase {
	
	static final Logger logger = Logger.getLogger(AppDatabase.class);
	
	/**
	 * The singleton instance of the database.
	 */
	private static AppDatabase instance;

	/**
	 * Get the singleton instance of the app database.
	 * 
	 * @return the singleton instance
	 */
	public static AppDatabase get() {
		if (instance == null) {
			instance = new AppDatabase();
		}
		return instance;
	}

	/**
	 * Construct a new app database.
	 */
	private AppDatabase() {

	}

	public BookDto[] loadAllBooks() {
		EntityManager em = EmfService.getFactory().createEntityManager();

		Query q = em.createQuery("select b from Book b order by b.id");
		
		ArrayList<Book> bookList = new ArrayList<Book>(q.getResultList());

		BookDto[] bookDtoArr = new BookDto[bookList.size()];

		for (int i = 0; i < bookList.size(); i++) {
			bookDtoArr[i] = bookList.get(i).getDto();
		}

		em.close();
		return bookDtoArr;
	}
	
	public BookDto getBook(Long id) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Book book = em.find(Book.class, id);
		BookDto bookDto = book == null ? null : book.getDto();
		em.close();
		return bookDto;
	}

	public BookDto createBook(BookDto bookDto) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Reader reader = em.find(Reader.class, bookDto.getReader().getId());
		
		em.getTransaction().begin();

		Book book = new Book(bookDto);
		book.setId(null); //if id == null then ROWID that is one larger than the largest ROWID in the table prior to the insert
		book.setReader(reader);
		
		em.persist(book);
		em.getTransaction().commit();
		em.close();
		
		return book.getDto();

	}

	public void updateBook(BookDto bookDto) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Book book = em.find(Book.class, bookDto.getId());
		Reader reader = em.find(Reader.class, bookDto.getReader().getId());

		em.getTransaction().begin();

		book.setDto(bookDto);

		book.setReader(reader);

		em.flush();
		em.getTransaction().commit();

		em.close();
	}

	public void deleteBook(BookDto bookDto) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Book book = em.find(Book.class, bookDto.getId());

		em.getTransaction().begin();

		em.remove(book);

		em.getTransaction().commit();

		em.close();
	}

	
	
	public NoteDto[] loadBookNotes(Long bookId) {
		EntityManager em = EmfService.getFactory().createEntityManager();

		Query q = em.createQuery("SELECT n FROM Note n WHERE n.book.id = :bookId ORDER BY n.id");
		q.setParameter("bookId", bookId);
		
		ArrayList<Note> noteList = new ArrayList<Note>(q.getResultList());

		NoteDto[] noteDtoArr = new NoteDto[noteList.size()];

		for (int i = 0; i < noteList.size(); i++) {
			noteDtoArr[i] = noteList.get(i).getDto();
		}

		em.close();
		return noteDtoArr;
	}

	public NoteDto getNote(Long id) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Note note = em.find(Note.class, id);
		NoteDto noteDto = note == null ? null : note.getDto();
		em.close();
		return noteDto;
	}
	
	public NoteDto createNote(NoteDto noteDto) throws MyBooksException {
		EntityManager em = EmfService.getFactory().createEntityManager();
		
		Book book = em.find(Book.class, noteDto.getBook().getId());
		
		em.getTransaction().begin();

		Note note = new Note(noteDto);
		note.setId(null); //if id == null then ROWID that is one larger than the largest ROWID in the table prior to the insert
		note.setBook(book);
		Date createdTime = new Date();
		note.setCreatedTime(createdTime);
		note.setModifiedTime(createdTime);

		em.getTransaction().commit();
		em.close();
		return note.getDto();

	}
	
	public NoteDto updateNote(NoteDto noteDto) throws MyBooksException {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Note note = em.find(Note.class, noteDto.getId());

		em.getTransaction().begin();

		note.setDto(noteDto);
		
		note.setModifiedTime(new Date());
		
		em.getTransaction().commit();
		em.close();
		
		return note.getDto();
		
	}

	public void deleteNote(NoteDto noteDto) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Note note = em.find(Note.class, noteDto.getId());

		em.getTransaction().begin();

		em.remove(note);

		em.getTransaction().commit();

		em.close();
	}
	

	
	public ReaderDto getReader(String username, String password) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		
		Query q = em.createQuery("SELECT r FROM Reader r WHERE r.username = :username AND r.password = :password");
		q.setParameter("username", username);
		q.setParameter("password", password);
		try {
			Reader reader = (Reader) q.getSingleResult();
			ReaderDto readerDto = reader == null? null : reader.getDto();
			em.close();
			return readerDto;
		} catch (NoResultException e) {
			logger.debug("Access denied for user " + username);
			return null;
		}		
	}
	
	public ReaderDto getReader(Long id) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Reader reader = em.find(Reader.class, id);
		ReaderDto readerDto = reader == null ? null : reader.getDto();
		em.close();
		return readerDto;
	}
	
	
	public ReaderDto createReader(ReaderDto readerDto) throws MyBooksException {
		EntityManager em = EmfService.getFactory().createEntityManager();
		
		em.getTransaction().begin();

		Reader reader = new Reader(readerDto);
		reader.setId(null); //if id == null then ROWID that is one larger than the largest ROWID in the table prior to the insert

		em.getTransaction().commit();
		em.close();
		return reader.getDto();

	}

	public ReaderDto updateReader(ReaderDto readerDto) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Reader reader = em.find(Reader.class, readerDto.getId());

		em.getTransaction().begin();

		reader.setDto(readerDto);

		em.flush();
		em.getTransaction().commit();

		em.close();
		
		return reader.getDto();
	}	
	
	public void deleteReader(ReaderDto readerDto) {
		EntityManager em = EmfService.getFactory().createEntityManager();
		Reader reader = em.find(Reader.class, readerDto.getId());

		em.getTransaction().begin();

		em.remove(reader);

		em.getTransaction().commit();

		em.close();
	}
	
		
	

}