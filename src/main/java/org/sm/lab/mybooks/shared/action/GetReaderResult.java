package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

public class GetReaderResult implements Result {
    private ReaderDto dto;

    GetReaderResult() {
    }

    public GetReaderResult(ReaderDto dto) {
        this.dto = dto;
    }

    public ReaderDto getDto() {
        return dto;
    }

    
}