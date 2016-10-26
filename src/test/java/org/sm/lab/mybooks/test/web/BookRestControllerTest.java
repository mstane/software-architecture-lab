package org.sm.lab.mybooks.test.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.test.web.util.WithMockCustomUser;

@WithMockCustomUser
public class BookRestControllerTest extends BaseRestControllerTest {

  private final String RESOURCE_PATH = "/rest/books/";

  @Test
  public void list() throws Exception {
    List<Book> books = loadBooks(12);
    
    mockMvc.perform(
    			get(RESOURCE_PATH + "?pageNumber=2&pageSize=5")
    		)    
	        .andExpect(status().isOk()).andExpect(content().contentType(jsonContentType))
	
	        .andExpect(jsonPath("$.number", is(2))).andExpect(jsonPath("$.last", is(true)))
	        .andExpect(jsonPath("$.size", is(5))).andExpect(jsonPath("$.numberOfElements", is(2)))
	        .andExpect(jsonPath("$.totalPages", is(3))).andExpect(jsonPath("$.first", is(false)))
	        .andExpect(jsonPath("$.totalElements", is(12)))
	
	        .andExpect(jsonPath("$.content", hasSize(2)))
	
	        .andExpect(jsonPath("$.content[0].id", is(books.get(10).getId().intValue())))
	        .andExpect(jsonPath("$.content[0].title", is(books.get(10).getTitle())))
	        .andExpect(jsonPath("$.content[0].author", is(books.get(10).getAuthor())))
	        .andExpect(jsonPath("$.content[1].id", is(books.get(11).getId().intValue())))
	        .andExpect(jsonPath("$.content[1].title", is(books.get(11).getTitle())))
	        .andExpect(jsonPath("$.content[1].author", is(books.get(11).getAuthor())));

  }

  @Test
  public void getOne() throws Exception {
    Book book = loadOneBook();
    
    mockMvc.perform(
    			get(RESOURCE_PATH + book.getId()))
    				.andExpect(status().isOk()
    		)
	        .andExpect(content().contentType(jsonContentType))
	        .andExpect(jsonPath("$.id", is(book.getId().intValue())))
	        .andExpect(jsonPath("$.title", is(book.getTitle())))
	        .andExpect(jsonPath("$.author", is(book.getAuthor())));
  }

  @Test
  public void search() throws Exception {	   
    loadBooks(12);
    mockMvc.perform(
    			get(RESOURCE_PATH + "?search=Deimos&pageNumber=2&pageSize=5")
    		)
	        .andExpect(status().isOk()).andExpect(content().contentType(jsonContentType))
	        
	        .andExpect(jsonPath("$.number", is(2)))
	        .andExpect(jsonPath("$.last", is(true)))
	        .andExpect(jsonPath("$.size", is(5)))
	        .andExpect(jsonPath("$.numberOfElements", is(2)))
	        .andExpect(jsonPath("$.totalPages", is(3)))
	        .andExpect(jsonPath("$.first", is(false)))
	        .andExpect(jsonPath("$.totalElements", is(12)))
	        
	        .andExpect(jsonPath("$.content", hasSize(2)))
	        
	        .andExpect(jsonPath("$.content[0].title", is("Book: Priamos Damokles Hyacinthus Deimos10")))
	
	        .andExpect(jsonPath("$.content[0].link", is("#/books/view/11")))
	        .andExpect(jsonPath("$.content[0].shortContent", is("Author: " + "Tuth Jeggregh K'gassen10" 
	        		+ "; Review: " + "The book is fictional, it doesn't exist and the data are used as placeholder names.10")));

  }
  

  @Test
  public void create() throws Exception {
    Book book = getOneBook();
    String bookJson = json(book);
    
    mockMvc.perform(
    			post(RESOURCE_PATH)
    				.with(csrf().asHeader())
    				.contentType(jsonContentType)
    				.content(bookJson)
    		)
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(jsonContentType))
	        .andExpect(jsonPath("$.id", is(1)))
	        .andExpect(jsonPath("$.title", is(book.getTitle())));
    
  }
  
  
  @Test
  public void update() throws Exception {
    Book book = loadOneBook();
    String titleUpdated = book.getTitle() + "-updated";
    book.setTitle(titleUpdated);
    String bookJson = json(book);

    mockMvc.perform(
    			put(RESOURCE_PATH + book.getId())
    				.with(csrf().asHeader())
    				.contentType(jsonContentType)
    				.content(bookJson)
    		)
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(jsonContentType))
	        .andExpect(jsonPath("$.id", is(book.getId().intValue())))
	        .andExpect(jsonPath("$.title", is(titleUpdated)));
  }

  @Test
  public void deleteOne() throws Exception {
    Book book = loadOneBook();
    
    mockMvc.perform(
    			delete(RESOURCE_PATH + book.getId())
    				.with(csrf().asHeader())
    		)
//    		.andDo(document("index"))
    		
    		.andExpect(status().isOk());
  }



}

