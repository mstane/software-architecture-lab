package org.sm.lab.mybooks3.test.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.HttpCookie;
import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sm.lab.mybooks3.MyBooks3Application;
import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.enums.Genre;
import org.sm.lab.mybooks3.enums.SystemRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyBooks3Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("test")
public class ApplicationTests {

	@Value("${local.server.port}")
	private int port;

	private RestTemplate template = new TestRestTemplate("root@root.com", "root");

	@Test
	public void homePageLoads() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void getPrincipal() {
		ResponseEntity<AuthenticationToken> response = template.getForEntity("http://localhost:"
				+ port + "/user", AuthenticationToken.class);
		
		UserDetailsDto userDetailsDto = response.getBody().getPrincipal();
		System.out.println("UserDetailsDto = " + userDetailsDto);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void bookPage() {
		ParameterizedTypeReference<PageDto<Book>> responseType = new ParameterizedTypeReference<PageDto<Book>>() { };
		
		ResponseEntity<PageDto<Book>> response = template.exchange(
				"http://localhost:" + port + "/rest/books?pageNumber=0&pageSize=5",
				HttpMethod.GET,
				null,
				responseType
		);
		
		PageDto<Book> page = response.getBody();
		System.out.println("Page=" + page);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void appData() {
		ParameterizedTypeReference<Map<String, List<String>>> responseType = new ParameterizedTypeReference<Map<String, List<String>>>() { };
		
		ResponseEntity<Map<String, List<String>>> response = template.exchange(
				"http://localhost:" + port + "/app_data",
				HttpMethod.GET,
				null,
				responseType
		);
		
		Map<String, List<String>> appData = response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		assertNotNull(appData);
		
		assertEquals(appData.get("genres"), Genre.names());
		assertEquals(appData.get("systemRoles"), SystemRole.names());
	}
	
	@Test
	public void registerReader() {
		TestRestTemplate registerReaderTemplate = new TestRestTemplate();
		ResponseEntity<String> response = registerReaderTemplate.getForEntity("http://localhost:" + port + "/", String.class);
		
		HttpHeaders headers = getHttpHeaders(response);
		
		Map<String, String> newReader = new HashMap<String, String>();
		newReader.put("username", "aaaa");
		newReader.put("email", "aaaa@aaaa.aaaa");
		newReader.put("password", "aaaa");
		RequestEntity<Map<String, String>> request = new RequestEntity<Map<String, String>>(
				newReader, headers, HttpMethod.POST, URI.create("http://localhost:" + port + "/register_reader"));
		response = registerReaderTemplate.exchange(request, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void forgottenPasswordSend() {
		ResponseEntity<String> response = new TestRestTemplate().getForEntity("http://localhost:" + port + "/forgotten_password_send?emailOrUsername=aaaa", String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	

//	@Test
	public void loginSucceeds() {
		ResponseEntity<String> response = template.getForEntity("http://localhost:"
				+ port + "/resource", String.class);
		String csrf = getCsrf(response.getHeaders());
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set("username", "user");
		form.set("password", "password");
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-XSRF-TOKEN", csrf);
		headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));
		RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(
				form, headers, HttpMethod.POST, URI.create("http://localhost:" + port
						+ "/login"));
		ResponseEntity<Void> location = template.exchange(request, Void.class);
		assertEquals("http://localhost:" + port + "/",
				location.getHeaders().getFirst("Location"));
	}
	
//	@Test
	public void loginSucceeds2() {
		String username = "root@root.com";
		String password = "root";
		String authString = "Basic " + username + ":" + password;
		
		String authStringEncoded = new String(Base64.encode(authString.getBytes()));
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.set("authorization", authStringEncoded);
		
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(requestHeaders);
		
		ResponseEntity<Principal> response = template.exchange(
				"http://localhost:" + port + "/user",
				HttpMethod.GET,
				requestEntity, 
				Principal.class);
		
		Principal principal = response.getBody();
		
		System.out.println("Principal: " + principal);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	
	private HttpHeaders getHttpHeaders(ResponseEntity<?> response) {
		String csrf = getCsrf(response.getHeaders());
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-XSRF-TOKEN", csrf);
		headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));
		return headers;
	}

	private String getCsrf(HttpHeaders headers) {
		for (String header : headers.get("Set-Cookie")) {
			List<HttpCookie> cookies = HttpCookie.parse(header);
			for (HttpCookie cookie : cookies) {
				if ("XSRF-TOKEN".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

}
