package org.sm.lab.mybooks3.security;

import org.sm.lab.mybooks3.domain.Book;
import org.sm.lab.mybooks3.domain.Reader;

public interface AuthorizationService {

	boolean isAdmin(UserDetailsImpl userDetails);
	
	boolean canAccessReader(UserDetailsImpl userDetails, Reader reader);
	
	boolean canAccessReader(UserDetailsImpl userDetails, String readerId);
    
    boolean canAccessBook(UserDetailsImpl userDetails, Book book);
    
    boolean canAccessBook(UserDetailsImpl userDetails, String bookId);
    
    boolean canAccessNote(UserDetailsImpl userDetails, String noteId);

}
