package org.sm.lab.mybooks.server.action;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.action.CreateReaderAction;
import org.sm.lab.mybooks.shared.action.CreateReaderResult;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class CreateReaderHandler implements ActionHandler<CreateReaderAction, CreateReaderResult> {
	
	static final Logger logger = Logger.getLogger(CreateReaderHandler.class);
	
	private final Provider<HttpSession> httpSession;

    public Class<CreateReaderAction> getActionType() {
        return CreateReaderAction.class;
    }
    
    @Inject
	public CreateReaderHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}


	@Override
	public CreateReaderResult execute(CreateReaderAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		ReaderDto dto = action.getDto();
		
		dto = AppDatabase.get().createReader(dto);

		return new CreateReaderResult(dto);
	}

	@Override
	public void rollback(CreateReaderAction action, CreateReaderResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}