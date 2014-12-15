package org.sm.lab.mybooks.server.action;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.CreateBookAction;
import org.sm.lab.mybooks.shared.action.CreateBookResult;
import org.sm.lab.mybooks.shared.dto.BookDto;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

public class CreateBookHandler implements ActionHandler<CreateBookAction, CreateBookResult> {

	static final Logger logger = Logger.getLogger(CreateBookHandler.class);
	
	private final Provider<HttpSession> httpSession;
	
	public Class<CreateBookAction> getActionType() {
        return CreateBookAction.class;
    }
	
    @Inject
	public CreateBookHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public CreateBookResult execute(CreateBookAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		ReaderDto readerDto = (ReaderDto)httpSession.get().getAttribute(AppConsts.READER);
		
		BookDto dto = action.getDto();
		dto.setReader(readerDto);
		dto = AppDatabase.get().createBook(dto);
		return new CreateBookResult(dto);
	}

	@Override
	public void rollback(CreateBookAction action, CreateBookResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}