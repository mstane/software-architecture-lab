package org.sm.lab.mybooks.test.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.test.web.util.WithMockCustomUser;

@WithMockCustomUser
public class ProfileRestControllerTest extends BaseRestControllerTest {

	private final String RESOURCE_PATH = "/rest/profiles/";

	@Before
	public void setup() throws Exception {
		super.setup();
	}

	@Test
	public void getProfile() throws Exception {
		Reader reader = loadOneReader();

		mockMvc.perform(
					get(RESOURCE_PATH + reader.getId())
				)
				
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))

				.andExpect(jsonPath("$.id", is(reader.getId().intValue())))
				.andExpect(jsonPath("$.username", is(reader.getUsername())))
				.andExpect(jsonPath("$.email", is(reader.getEmail())))
				.andExpect(jsonPath("$.password", is(reader.getPassword())));
	}

	@Test
	public void updateProfile() throws Exception {
		Reader reader = loadOneReader();
		String usernameUpdated = reader.getUsername() + "-updated";
		reader.setUsername(usernameUpdated);
		String readerJson = json(reader);

		mockMvc.perform(
					put(RESOURCE_PATH + reader.getId())
						.with(csrf().asHeader())
						.contentType(contentType)
						.content(readerJson)
				)

				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				
				.andExpect(jsonPath("$.id", is(reader.getId().intValue())))
				.andExpect(jsonPath("$.username", is(usernameUpdated)));
	}

}
