package org.sm.lab.mybooks3.security;



public interface AuthorizationService {

    boolean canAccessUser(CurrentUser reader, Long userId);

}
