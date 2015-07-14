package org.sm.lab.mybooks3.security;

import org.sm.lab.mybooks3.domain.Book;

public interface AuthorizationService {

    boolean canAccessUser(UserDetailsImpl userDetails, Long userId);
    
    boolean canUpdateBook(UserDetailsImpl userDetails, Book book);
    
    boolean canUpdateBook(UserDetailsImpl userDetails, Long bookId);

}
