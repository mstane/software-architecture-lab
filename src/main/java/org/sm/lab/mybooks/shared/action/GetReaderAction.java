package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class GetReaderAction implements Action<GetReaderResult> {

	private ReaderDto dto;
	
    GetReaderAction() {
    }
    
    public GetReaderAction(ReaderDto dto) {
    	this.dto = dto;
    }

    public ReaderDto getDto() {
    	return this.dto;
    }
    
}