package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.UpdateBookAction;
import org.sm.lab.mybooks.shared.action.UpdateBookResult;
import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class UpdateBookHandler implements ActionHandler<UpdateBookAction, UpdateBookResult> {
	
	static final Logger logger = Logger.getLogger(UpdateBookHandler.class);
	
	private final Provider<HttpSession> httpSession;
	
    public Class<UpdateBookAction> getActionType() {
        return UpdateBookAction.class;
    }
    
    @Inject
	public UpdateBookHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public UpdateBookResult execute(UpdateBookAction action, ExecutionContext context) throws DispatchException {
		
		logger.debug("Start execute.");
		
		ReaderDto readerDto = (ReaderDto)httpSession.get().getAttribute(AppConsts.READER);
		
		BookDto dto = action.getDto();
		dto.setReader(readerDto);
		AppDatabase.get().updateBook(dto);
		return new UpdateBookResult();
	}

	@Override
	public void rollback(UpdateBookAction action, UpdateBookResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}