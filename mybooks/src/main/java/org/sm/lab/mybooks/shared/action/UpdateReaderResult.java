package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class UpdateReaderResult implements Result {

    private ReaderDto dto;
    
    UpdateReaderResult() {
    }
    
    public UpdateReaderResult(ReaderDto dto) {
        this.dto = dto;
    }

    public ReaderDto getDto() {
        return dto;
    }
    
}