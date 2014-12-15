package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.BookDto;

public class CreateBookResult implements Result {
    private BookDto dto;

    CreateBookResult() {
    }

    public CreateBookResult(BookDto dto) {
        this.dto = dto;
    }

    public BookDto getDto() {
        return dto;
    }

    
}