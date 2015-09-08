package org.sm.lab.mybooks.security;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Reader;

public interface AuthorizationService {

	boolean isAdmin(UserDetailsImpl userDetails);
	
	boolean canAccessReader(UserDetailsImpl userDetails, Reader reader);
	
	boolean canAccessReader(UserDetailsImpl userDetails, Long readerId);
    
    boolean canAccessBook(UserDetailsImpl userDetails, Book book);
    
    boolean canAccessBook(UserDetailsImpl userDetails, Long bookId);
    
    boolean canAccessNote(UserDetailsImpl userDetails, Long noteId);

}
