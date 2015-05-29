package org.sm.lab.mybooks3.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
	
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello!");
		return model;
	}
	
	@RequestMapping(value = "/forgotten_password_send")
	public ResponseEntity<String> forgottenPassword(@RequestParam(value = "emailOrUsername", required = true) String emailOrUsername) {
		HttpStatus httpStatus;
		String message;
		
		Optional<Reader> reader = readerService.findByEmail(emailOrUsername);
        if (!reader.isPresent()) {
        	reader = readerService.findByUsername(emailOrUsername);
        }
        if (!reader.isPresent()) {
        	httpStatus = HttpStatus.NOT_FOUND;
        	message = String.format("User with emailOrUsername=%s was not found", emailOrUsername);
        } else {
        	try {
        		sendMessage(reader.get().getEmail(), "Forgotten password", "Your password is: " + reader.get().getPassword());        		
        		httpStatus = HttpStatus.OK;
        		message = "Your password has been successfully sent to your mail.";
			} catch (MailException e) {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				message = e.getMessage();
			}
        }
        
        return getMessageResponse(httpStatus, message);

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
	
	
}
