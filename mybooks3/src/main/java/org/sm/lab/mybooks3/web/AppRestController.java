package org.sm.lab.mybooks3.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public void forgottenPassword(@RequestParam(value = "emailOrUsername", required = true) String emailOrUsername) {
        Optional<Reader> reader = readerService.findByEmail(emailOrUsername);
        if (!reader.isPresent()) {
        	reader = readerService.findByUsername(emailOrUsername);
        }
        reader.orElseThrow(() -> new UsernameNotFoundException(String.format("User with emailOrUsername=%s was not found", emailOrUsername)));

		sendMessage(reader.get().getEmail(), "Forgotten password", "Your password is:" + reader.get().getPassword());
	}
	
    private void sendMessage(String mailTo, String subject, String message) {
        org.springframework.mail.SimpleMailMessage mailMessage = new org.springframework.mail.SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(mailTo);
        mailMessage.setText(message);
        mailTemplate.send(mailMessage);
    }
	
	
}
