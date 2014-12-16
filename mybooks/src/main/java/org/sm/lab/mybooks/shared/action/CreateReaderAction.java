package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class CreateReaderAction implements Action<CreateReaderResult> {

	private ReaderDto dto;
	
    CreateReaderAction() {
    }
    
    public CreateReaderAction(ReaderDto dto) {
    	this.dto = dto;
    }

    public ReaderDto getDto() {
    	return this.dto;
    }
    
}