package org.sm.lab.mybooks.server.action;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.action.DeleteReaderAction;
import org.sm.lab.mybooks.shared.action.DeleteReaderResult;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class DeleteReaderHandler implements ActionHandler<DeleteReaderAction, DeleteReaderResult> {
	
	static final Logger logger = Logger.getLogger(DeleteReaderHandler.class);
	
	private final Provider<HttpSession> httpSession;
	
    public Class<DeleteReaderAction> getActionType() {
        return DeleteReaderAction.class;
    }
    
    @Inject
	public DeleteReaderHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public DeleteReaderResult execute(DeleteReaderAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		ReaderDto dto = action.getDto();
		AppDatabase.get().deleteReader(dto);
		return new DeleteReaderResult();
	}

	@Override
	public void rollback(DeleteReaderAction action, DeleteReaderResult result, ExecutionContext context) throws DispatchException {
		
	}

	
}