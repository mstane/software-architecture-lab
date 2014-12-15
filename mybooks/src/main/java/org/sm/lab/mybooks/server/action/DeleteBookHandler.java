package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.DeleteBookAction;
import org.sm.lab.mybooks.shared.action.DeleteBookResult;
import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class DeleteBookHandler implements ActionHandler<DeleteBookAction, DeleteBookResult> {
	
	static final Logger logger = Logger.getLogger(DeleteBookHandler.class);
	
	private final Provider<HttpSession> httpSession;
	
    public Class<DeleteBookAction> getActionType() {
        return DeleteBookAction.class;
    }
    
    @Inject
	public DeleteBookHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public DeleteBookResult execute(DeleteBookAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		ReaderDto readerDto = (ReaderDto)httpSession.get().getAttribute(AppConsts.READER);
		
		BookDto dto = action.getDto();
		dto.setReader(readerDto);
		AppDatabase.get().deleteBook(dto);
		return new DeleteBookResult();
	}

	@Override
	public void rollback(DeleteBookAction action, DeleteBookResult result, ExecutionContext context) throws DispatchException {
		
	}

	
}