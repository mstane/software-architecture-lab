package org.sm.lab.mybooks.security.permission;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Reader;
import org.sm.lab.mybooks.repository.BookRepository;
import org.sm.lab.mybooks.security.AppPermission;
import org.sm.lab.mybooks.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

public class BookNotesAuthorPermission extends AppPermission {

	@Autowired
	BookRepository bookRepository;
	
	@Override
    public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
        boolean hasPermission = false;
        Book book = null;
        if (isAuthenticated(authentication) && isValidId(targetDomainObject)) {
        	book = bookRepository.findOne((Long) targetDomainObject);
        	
        	if (book != null) {
            	UserDetailsImpl userDetails = getUserDetails(authentication);
            	Reader bookNoteAuthor = book.getReader();
                if ((bookNoteAuthor != null && bookNoteAuthor.getId().equals(userDetails.getId())) 
                		|| userDetails.isAdmin()){
                    hasPermission = true;
                }
            }
        }
        
        return hasPermission;
    }

}
