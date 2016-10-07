package org.sm.lab.mybooks.test.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.test.web.util.WithMockCustomUser;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;


public class AppRestControllerTest extends BaseRestControllerTest {
	
	protected MediaType htmlContentType = new MediaType(MediaType.TEXT_HTML.getType(),
			MediaType.TEXT_HTML.getSubtype(), Charset.forName("utf8"));

	@Before
	public void setup() throws Exception {
		super.setup();
	}

	@Test
	public void homePageLoads() throws Exception {
		mockMvc.perform(
					get("/")
				)
				
				.andExpect(status().isOk())
				.andExpect(content().contentType(htmlContentType));
	}
	
	@Test
	@WithMockCustomUser
	public void getPrincipal() throws Exception {
		mockMvc.perform(
				get("/user")
			)
			.andDo(print())
			
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType));
	}

	@Test
	public void appData() throws Exception {
		mockMvc.perform(
				get("/app_data")
			)
			.andDo(print())
			
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType));
	}
	
	@Test
	public void registerReader() throws Exception {
		Reader reader = getOneReader();
		String readerJson = json(reader);

		mockMvc.perform(
				post("/register_reader")
					.with(csrf().asHeader())
					.contentType(contentType)
					.content(readerJson)
				)
				.andDo(print())

				.andExpect(status().isOk());
		
	}

	@Test
	public void forgottenPasswordSend() throws Exception {
		mockMvc.perform(
				get("/forgotten_password_send?email=aaaa")
			)
			.andDo(print())
			
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void loginSucceeds() throws Exception {
		Reader reader = loadOneReader();
		
		String authString = reader.getEmail() + ":" + "Mb.1234";		
		String authStringEncoded = new String(Base64.encode(authString.getBytes()));
		
		mockMvc.perform(
				get("/user").header("Authorization", "Basic " + authStringEncoded)
			)
			.andDo(print())
			
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType));
	}

	
}
