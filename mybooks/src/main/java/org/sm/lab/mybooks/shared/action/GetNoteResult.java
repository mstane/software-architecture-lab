package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class GetNoteResult implements Result {
    private NoteDto dto;

    GetNoteResult() {
    }

    public GetNoteResult(NoteDto dto) {
        this.dto = dto;
    }

    public NoteDto getDto() {
        return dto;
    }

    
}