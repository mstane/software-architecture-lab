package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.BookDto;

import java.util.List;

public class LoadAllBooksResult implements Result {
    private List<BookDto> dtos;

    LoadAllBooksResult() {
    }

    public LoadAllBooksResult(List<BookDto> dtos) {
        this.dtos = dtos;
    }

    public List<BookDto> getDtos() {
        return dtos;
    }

    
}