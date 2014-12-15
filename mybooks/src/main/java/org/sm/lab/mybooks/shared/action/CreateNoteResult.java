package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class CreateNoteResult implements Result {
    private NoteDto dto;

    CreateNoteResult() {
    }

    public CreateNoteResult(NoteDto dto) {
        this.dto = dto;
    }

    public NoteDto getDto() {
        return dto;
    }

    
}