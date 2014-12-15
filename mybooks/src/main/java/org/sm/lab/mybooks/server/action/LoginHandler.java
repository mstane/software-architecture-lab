package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.LoginAction;
import org.sm.lab.mybooks.shared.action.LoginResult;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class LoginHandler implements ActionHandler<LoginAction, LoginResult> {
	
	static final Logger logger = Logger.getLogger(LoginHandler.class);

	private final Provider<HttpSession> httpSession;
	
    public Class<LoginAction> getActionType() {
        return LoginAction.class;
    }
    
    @Inject
	public LoginHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public LoginResult execute(LoginAction action, ExecutionContext context) throws DispatchException {
		
		logger.debug("Start execute.");
		
		ReaderDto readerDto = null;
		int duration = 0;
		
		if (action.getSessionId() != null) {
			Object userObj = httpSession.get().getAttribute(AppConsts.READER);
			if (userObj != null && userObj instanceof ReaderDto) {
				readerDto = (ReaderDto) userObj;
				duration = readerDto.getSessionDuration();
			}
		} else if (action.getUsername() != null && action.getPassword() != null) {
		    duration = action.isRememberMe() ? AppConsts.SESSION_DUARTION_EXTENDED_SECONDS : AppConsts.SESSION_DUARTION_COMMON_SECONDS;
		    readerDto = doAuthentication(action.getUsername(), action.getPassword());
			if (readerDto != null) {
				httpSession.get().setAttribute(AppConsts.READER, readerDto);
				httpSession.get().setMaxInactiveInterval(duration);
				readerDto.setSessionDuration(duration);
			}
		}
		String sessionId = httpSession.get().getId();
		
		return new LoginResult(readerDto, sessionId, duration);
		
	}

	private ReaderDto doAuthentication(String username, String password) {
		return AppDatabase.get().getReader(username, password);
	}
	
	@Override
	public void rollback(LoginAction action, LoginResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}