package org.sm.lab.mybooks.server;

import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AppSecureSessionValidator implements SecureSessionValidator {
    
    static final Logger logger = Logger.getLogger(AppSecureSessionValidator.class);
    
	@Override
	public boolean isValid(String clientSessionId, HttpServletRequest req) {
		HttpSession session = req.getSession();
		String serverSessionId = session.getId();
		ReaderDto readerDto = (ReaderDto)session.getAttribute(AppConsts.READER);
		
		if (clientSessionId != null && readerDto != null && clientSessionId.equals(serverSessionId)) {
			return true;
		}
		return false;
	}

}
