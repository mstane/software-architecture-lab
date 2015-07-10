package org.sm.lab.mybooks3.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.enums.Genre;
import org.sm.lab.mybooks3.enums.SystemRole;
import org.sm.lab.mybooks3.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AppRestController {
	
    @Autowired
    private MailSender mailTemplate;
    
    @Autowired
    private ReaderService readerService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
	@RequestMapping(value = "/register_reader", method = RequestMethod.POST)
	public ResponseEntity<String> registerReader(@RequestBody @Valid Reader reader) {
		HttpStatus httpStatus = null;
		String message = null;
		try {
    		reader.setSystemRole(SystemRole.Common);
    		reader.setPassword(encodePassword(reader.getPassword()));
    		readerService.saveReader(reader);
       		httpStatus = HttpStatus.OK;
       		message = "You have successfully registered.";
		} catch (Exception e) {
			httpStatus = HttpStatus.BAD_REQUEST;
			message = e.getMessage();
		}
		
		return getMessageResponse(httpStatus, message);
		
	}
	
	@RequestMapping(value = "/forgotten_password_send")
	public ResponseEntity<String> forgottenPassword(@RequestParam(value = "emailOrUsername", required = true) String emailOrUsername) {
		HttpStatus httpStatus;
		String message;
		
		Optional<Reader> readerOpt = readerService.findByEmail(emailOrUsername);
        if (!readerOpt.isPresent()) {
        	readerOpt = readerService.findByUsername(emailOrUsername);
        }
        if (!readerOpt.isPresent()) {
        	httpStatus = HttpStatus.NOT_FOUND;
        	message = String.format("User with emailOrUsername=%s was not found", emailOrUsername);
        } else {
        	try {
        		Reader reader = readerOpt.get();
        		String generatedPassword = generatePassword(); 
        		reader.setPassword(encodePassword(generatedPassword));
        		readerService.saveReader(reader);
        		sendMessage(reader.getEmail(), "Forgotten password", "Your password is: " + generatedPassword);        		
        		httpStatus = HttpStatus.OK;
        		message = "Your password has been successfully sent to your mail.";
			} catch (MailException e) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				message = e.getMessage();
			}
        }
        
        return getMessageResponse(httpStatus, message);

	}
	
	private String generatePassword() {
		String password = RandomStringUtils.random(10, true, true);
		return password;
	}
	
	private String encodePassword(String password) {
		if (password != null) {
			password  = passwordEncoder.encode(password);
			return password;
		}
		return null;
	}
	
	private ResponseEntity<String> getMessageResponse(HttpStatus httpStatus, String message) {
		Map<String, Object> messageJson = new HashMap<String, Object>();
		messageJson.put("message", message);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			message = mapper.writeValueAsString(messageJson);
		} catch (JsonProcessingException e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			message = "{ \"message\" : \"Json Processing Exception\" }";
		}
		return new ResponseEntity<String>(message , httpStatus);
	}
	
    private void sendMessage(String mailTo, String subject, String message) throws MailException {
        org.springframework.mail.SimpleMailMessage mailMessage = new org.springframework.mail.SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(mailTo);
        mailMessage.setText(message);
        mailTemplate.send(mailMessage);
    }
    
	@RequestMapping(value = "/app_data")
	public ResponseEntity<Map<String, List<String>>> appData() {
		
		Map<String, List<String>> appData = new HashMap<String, List<String>>();
		appData.put("genres", Genre.names());
		appData.put("systemRoles", SystemRole.names());
		
		return new ResponseEntity<Map<String, List<String>>>(appData, HttpStatus.OK);
	}
    
	
}
