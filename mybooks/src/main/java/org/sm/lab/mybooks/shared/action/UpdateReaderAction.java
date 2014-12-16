package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class UpdateReaderAction implements Action<UpdateReaderResult> {

	private ReaderDto dto;
	
    UpdateReaderAction() {
    }
    
    public UpdateReaderAction(ReaderDto dto) {
    	this.dto = dto;
    }

    public ReaderDto getDto() {
    	return this.dto;
    }
    
}