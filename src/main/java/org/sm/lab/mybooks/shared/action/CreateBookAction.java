package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.BookDto;

public class CreateBookAction implements Action<CreateBookResult> {

	private BookDto dto;
	
    CreateBookAction() {
    }
    
    public CreateBookAction(BookDto dto) {
    	this.dto = dto;
    }

    public BookDto getDto() {
    	return this.dto;
    }
    
}