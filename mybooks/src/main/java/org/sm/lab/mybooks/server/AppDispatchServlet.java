package org.sm.lab.mybooks.server;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.guice.GuiceSecureDispatchServlet;
import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;

import org.apache.log4j.Logger;

/**
 * 
 * This extension for existing GuiceSecureDispatchServlet is for enabling to have
 * action which doesn't require authentication i.e. LoginAction
 * 
 * @author Stanislav Markov
 *
 */

@Singleton
public class AppDispatchServlet extends GuiceSecureDispatchServlet {

	private static final long serialVersionUID = 1L;
	
	static final Logger logger = Logger.getLogger(AppDispatchServlet.class);
	
	@Inject
	public AppDispatchServlet(Dispatch dispatch, SecureSessionValidator sessionValidator) {
		super(dispatch, sessionValidator);
	}

	@Override
    public Result execute(String sessionId, Action<?> action) throws DispatchException {
        logger.debug("execute():sessionId=" + sessionId + "; action.toString()=" + action.toString());
	    if (isAnonymousAction(action)) {
            return getDispatch().execute(action);
        }
        return super.execute(sessionId, action);
    }

	private boolean isAnonymousAction(Action<?> action) {
		return action.toString().contains("LoginAction")
				|| action.toString().contains("LogoutAction");
	}
	
	

}
