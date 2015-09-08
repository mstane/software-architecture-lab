package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class DeleteReaderAction implements Action<DeleteReaderResult> {

	private ReaderDto dto;
	
	DeleteReaderAction() {
    }
    
    public DeleteReaderAction(ReaderDto dto) {
    	this.dto = dto;
    }

    public ReaderDto getDto() {
    	return this.dto;
    }
    
}