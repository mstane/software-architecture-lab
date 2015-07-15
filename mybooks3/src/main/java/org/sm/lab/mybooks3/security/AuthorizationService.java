package org.sm.lab.mybooks3.security;

import org.sm.lab.mybooks3.domain.Book;

public interface AuthorizationService {

    boolean canAccessUser(UserDetailsImpl userDetails, Long userId);
    
    boolean canAccessBook(UserDetailsImpl userDetails, Book book);
    
    boolean canAccessBook(UserDetailsImpl userDetails, Long bookId);
    
    boolean canAccessNote(UserDetailsImpl userDetails, Long noteId);

}
