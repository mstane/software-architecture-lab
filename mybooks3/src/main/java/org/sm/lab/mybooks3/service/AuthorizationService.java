package org.sm.lab.mybooks3.service;

import org.sm.lab.mybooks3.CurrentUser;


public interface AuthorizationService {

    boolean canAccessUser(CurrentUser reader, Long userId);

}
