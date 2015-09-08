package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class UpdateNoteResult implements Result {

    private NoteDto dto;
    
    UpdateNoteResult() {
    }
    
    public UpdateNoteResult(NoteDto dto) {
        this.dto = dto;
    }

    public NoteDto getDto() {
        return dto;
    }
    
}