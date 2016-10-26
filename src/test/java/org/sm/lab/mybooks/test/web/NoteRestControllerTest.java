package org.sm.lab.mybooks.test.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.sm.lab.mybooks.domain.Note;
import org.sm.lab.mybooks.test.web.util.WithMockCustomUser;

@WithMockCustomUser
public class NoteRestControllerTest extends BaseRestControllerTest {

	private final String RESOURCE_PATH = "/rest/notes/";

	@Test
	public void getOne() throws Exception {
		Note note = loadOneNote();
		
		mockMvc.perform(
					get(RESOURCE_PATH + note.getId())
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(jsonContentType)).andExpect(jsonPath("$.id", is(note.getId().intValue())))
				.andExpect(jsonPath("$.title", is(note.getTitle())))
				.andExpect(jsonPath("$.content", is(note.getContent())));
	}

	@Test
	public void create() throws Exception {
		Note note = getOneNote();
		String noteJson = json(note);

		mockMvc.perform(
					post(RESOURCE_PATH + "?bookId=" + note.getBook().getId())
						.with(csrf().asHeader())
						.contentType(jsonContentType)
						.content(noteJson)
				)
				.andExpect(status().isOk()).andExpect(content().contentType(jsonContentType))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.title", is(note.getTitle())));
	}

	@Test
	public void update() throws Exception {
		Note note = loadOneNote();
		String titleUpdated = note.getTitle() + "-updated";
		note.setTitle(titleUpdated);
		String noteJson = json(note);

		mockMvc.perform(
					put(RESOURCE_PATH + note.getId() + "?bookId=" + note.getBook().getId())
						.with(csrf().asHeader())
						.contentType(jsonContentType).content(noteJson)).andDo(print()
				)
				.andExpect(status().isOk()).andExpect(content().contentType(jsonContentType))
				.andExpect(jsonPath("$.id", is(note.getId().intValue())))
				.andExpect(jsonPath("$.title", is(titleUpdated)));
	}

	@Test
	public void deleteOne() throws Exception {
		Note note = loadOneNote();
		
		mockMvc.perform(
					delete(RESOURCE_PATH + note.getId())
						.with(csrf().asHeader())
				)
				.andExpect(status().isOk());
		
	}

}
