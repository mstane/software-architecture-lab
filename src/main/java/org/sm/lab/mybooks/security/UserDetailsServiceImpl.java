package org.sm.lab.mybooks.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ReaderService readerService;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) {
        LOGGER.debug("Authenticating user with email={}", email.replaceFirst("@.*", "@***"));
        Reader reader = readerService.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new UserDetailsImpl(reader);
    }

}
