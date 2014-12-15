package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.LogoutAction;
import org.sm.lab.mybooks.shared.action.LogoutResult;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class LogoutHandler implements ActionHandler<LogoutAction, LogoutResult> {
	
	static final Logger logger = Logger.getLogger(LogoutHandler.class);
	
	private final Provider<HttpSession> httpSession;
	
    public Class<LogoutAction> getActionType() {
        return LogoutAction.class;
    }

    @Inject
	public LogoutHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}
    
	@Override
	public LogoutResult execute(LogoutAction action, ExecutionContext context) throws DispatchException {
		
		logger.debug("Start execute.");

		HttpSession session =  httpSession.get();
		
		ReaderDto user = (ReaderDto)session.getAttribute(AppConsts.READER);
		
		if (user != null) {
			session.removeAttribute(AppConsts.READER);
		}
		session.invalidate();
		
		return new LogoutResult();
	}

	@Override
	public void rollback(LogoutAction action, LogoutResult result, ExecutionContext context) throws DispatchException {
		
	}

	
}