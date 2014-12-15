package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.BookDto;

public class DeleteBookAction implements Action<DeleteBookResult> {

	private BookDto dto;
	
    DeleteBookAction() {
    }
    
    public DeleteBookAction(BookDto dto) {
    	this.dto = dto;
    }

    public BookDto getDto() {
    	return this.dto;
    }
    
}