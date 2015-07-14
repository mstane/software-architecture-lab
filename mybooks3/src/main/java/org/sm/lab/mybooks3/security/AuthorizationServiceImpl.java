package org.sm.lab.mybooks3.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.Reader;
import org.sm.lab.mybooks3.enums.SystemRole;
import org.sm.lab.mybooks3.repository.BookRepository;
import org.sm.lab.mybooks3.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);

	@Autowired
	ReaderRepository readerRepository;
    
	@Autowired
	BookRepository bookRepository;
    
    @Override
    public boolean canAccessUser(UserDetailsImpl userDetails, Long userId) {
        LOGGER.debug("Checking if user={} has access to user={}", userDetails, userId);
        return userDetails != null
                && (userDetails.getSystemRole() == SystemRole.Admin 
                	|| userDetails.getId().equals(userId)
                	);
    }

	@Override
	public boolean canUpdateBook(UserDetailsImpl userDetails, Book book) {
		if (book.getReader() == null) {
	        Reader reader = readerRepository.findOne(userDetails.getId());
	        book.setReader(reader);
	        return true;
		} else {
			return canAccessUser(userDetails, book.getReader().getId());
		}
	}

	@Override
	public boolean canUpdateBook(UserDetailsImpl userDetails, Long bookId) {
		if (bookId == null || userDetails == null) return false;
		
		Book book = bookRepository.findOne(bookId);
		
		if (book == null) {
			return false;
		} else {
			return canAccessUser(userDetails, book.getReader().getId());
		}

	}

}
