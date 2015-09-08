package org.sm.lab.mybooks.server.action;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.action.UpdateReaderAction;
import org.sm.lab.mybooks.shared.action.UpdateReaderResult;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class UpdateReaderHandler implements ActionHandler<UpdateReaderAction, UpdateReaderResult> {
	
	static final Logger logger = Logger.getLogger(UpdateReaderHandler.class);
	
	private final Provider<HttpSession> httpSession;
	
    public Class<UpdateReaderAction> getActionType() {
        return UpdateReaderAction.class;
    }
    
    @Inject
	public UpdateReaderHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public UpdateReaderResult execute(UpdateReaderAction action, ExecutionContext context) throws DispatchException {
		
		logger.debug("Start execute.");
		
		ReaderDto dto = action.getDto();
		
		AppDatabase.get().updateReader(dto);
		
		return new UpdateReaderResult(dto);
	}

	@Override
	public void rollback(UpdateReaderAction action, UpdateReaderResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}