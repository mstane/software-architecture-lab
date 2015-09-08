package org.sm.lab.mybooks.security;

import org.sm.lab.mybooks.domain.Book;
import org.sm.lab.mybooks.domain.Reader;

public interface AuthorizationService {

	boolean isAdmin(UserDetailsImpl userDetails);
	
	boolean canAccessReader(UserDetailsImpl userDetails, Reader reader);
	
	boolean canAccessReader(UserDetailsImpl userDetails, String readerId);
    
    boolean canAccessBook(UserDetailsImpl userDetails, Book book);
    
    boolean canAccessBook(UserDetailsImpl userDetails, String bookId);
    
    boolean canAccessNote(UserDetailsImpl userDetails, String noteId);

}
