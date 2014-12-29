package org.sm.lab.mybooks2.security.permission;

import org.sm.lab.mybooks2.domain.Book;
import org.sm.lab.mybooks2.domain.Reader;
import org.sm.lab.mybooks2.repository.BookRepository;
import org.sm.lab.mybooks2.security.AppPermission;
import org.sm.lab.mybooks2.security.UserDetailsImpl;
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
        	book = bookRepository.findOne((String) targetDomainObject);
        	
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
