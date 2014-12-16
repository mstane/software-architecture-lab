package org.sm.lab.mybooks.server.action;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.apache.log4j.Logger;
import org.sm.lab.mybooks.server.AppDatabase;
import org.sm.lab.mybooks.shared.AppConsts;
import org.sm.lab.mybooks.shared.action.GetReaderAction;
import org.sm.lab.mybooks.shared.action.GetReaderResult;
import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class GetReaderHandler implements ActionHandler<GetReaderAction, GetReaderResult> {
	
	static final Logger logger = Logger.getLogger(GetReaderHandler.class);

	private final Provider<HttpSession> httpSession;
	
    public Class<GetReaderAction> getActionType() {
        return GetReaderAction.class;
    }
    
    @Inject
	public GetReaderHandler(final Provider<HttpSession> httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public GetReaderResult execute(GetReaderAction action, ExecutionContext context) throws DispatchException {
		logger.debug("Start execute.");
		
		ReaderDto dto = null;
		
		ReaderDto readerDto = (ReaderDto)httpSession.get().getAttribute(AppConsts.READER);
		if (readerDto != null && readerDto.getId() != null) {
			dto = AppDatabase.get().getReader(readerDto.getId());
		} else {
			dto = new ReaderDto();
		}
		
		return new GetReaderResult(dto);
	}

	@Override
	public void rollback(GetReaderAction action, GetReaderResult result, ExecutionContext context) throws DispatchException {
		
	}
	
}