package org.sm.lab.mybooks.test.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.sm.lab.mybooks.MyBooksApplication;
import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Note;
import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.enums.Genre;
import org.sm.lab.mybooks.enums.SystemRole;
import org.sm.lab.mybooks.repository.data.BookRepository;
import org.sm.lab.mybooks.repository.data.NoteRepository;
import org.sm.lab.mybooks.repository.data.ReaderRepository;
import org.sm.lab.mybooks.test.web.util.WithMockCustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyBooksApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class BaseRestControllerTest {

  protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

  protected MockMvc mockMvc;

  protected HttpMessageConverter mappingJackson2HttpMessageConverter;
  
  @Autowired
  protected PasswordEncoder passwordEncoder;

  @Autowired
  protected WebApplicationContext webApplicationContext;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private ReaderRepository readerRepository;

  @Autowired
  private NoteRepository noteRepository;


  @Autowired
  protected void setConverters(HttpMessageConverter<?>[] converters) {

    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

    Assert.assertNotNull("the JSON message converter must not be null",
        this.mappingJackson2HttpMessageConverter);
  }

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .build();
  }

  protected String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON,
        mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }

  protected Book loadOneBook() {
    return loadBooks(1).get(0);
  }

  protected List<Book> loadBooks(int number) {
    Reader reader = loadOneReader();
    Book book;
    List<Book> books = new ArrayList<Book>();

    for (int i = 0; i < number; i++) {
      book = new Book();
      book.setTitle("Priamos Damokles Hyacinthus Deimos" + i);
      book.setAuthor("Tuth Jeggregh K'gassen" + i);
      book.setRating((long) number);
      book.setGenre(Genre.Satire);
      book.setReview(
          "The book is fictional, it doesn't exist and the data are used as placeholder names."
              + i);
      book.setStartReadingDate(new Date());
      book.setEndReadingDate(new Date());
      book.setReader(reader);
      book = bookRepository.save(book);
      books.add(book);
    }

    return books;
  }

  protected Reader loadOneReader() {
    return loadReaders(1).get(0);
  }

  protected List<Reader> loadReaders(int number) {
    Reader reader;
    List<Reader> readers = new ArrayList<Reader>();

    for (int i = 0; i < number; i++) {
      reader = new Reader();
      reader.setUsername("Agarf Rac" + i);
      reader.setEmail("agarf.rac" + i + "@example.com");
      reader.setPassword("Mb.1234");
      reader.setSystemRole(SystemRole.Common);
      reader = readerRepository.save(reader);
      readers.add(reader);
    }

    return readers;
  }

  protected Reader loadMainReader() {
    Reader reader = new Reader();
    reader.setUsername("Agarf Rac");
    reader.setEmail("agarf.rac@example.com");
    reader.setPassword("Mb.1234");
    reader.setSystemRole(SystemRole.Common);
    reader = readerRepository.save(reader);
    return reader;
  }
  
  protected Note loadOneNote() {
    return loadNotes(1).get(0);
  }

  protected List<Note> loadNotes(int number) {
    Book book = loadOneBook();
    
    Note note;
    List<Note> notes = new ArrayList<>();

    for (int i = 0; i < number; i++) {
      note = new Note();
      note.setBook(book);
      note.setTitle("Man and dolphins" + i);
      note.setContent(
          "The dolphins had always believed that they were far more intelligent than man ..." + i);
      note.setCreatedTime(new Date());
      note.setModifiedTime(new Date());
      note = noteRepository.save(note);
      notes.add(note);
    }

    return notes;
  }

}