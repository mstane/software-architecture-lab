package org.sm.lab.mybooks3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sm.lab.mybooks3.CurrentUser;
import org.sm.lab.mybooks3.enums.SystemRole;
import org.springframework.stereotype.Service;

@Service("authorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);

    @Override
    public boolean canAccessUser(CurrentUser reader, Long userId) {
        LOGGER.debug("Checking if user={} has access to user={}", reader, userId);
        return reader != null
                && (reader.getSystemRole() == SystemRole.Admin || reader.getId().equals(userId));
    }

}
