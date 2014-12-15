package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.BookDto;

public class UpdateBookAction implements Action<UpdateBookResult> {

	private BookDto dto;
	
    UpdateBookAction() {
    }
    
    public UpdateBookAction(BookDto dto) {
    	this.dto = dto;
    }

    public BookDto getDto() {
    	return this.dto;
    }
    
}