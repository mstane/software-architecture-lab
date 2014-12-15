package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Result;

import org.sm.lab.mybooks.shared.dto.NoteDto;

import java.util.List;

public class LoadAllNotesResult implements Result {
    private List<NoteDto> dtos;

    LoadAllNotesResult() {
    }

    public LoadAllNotesResult(List<NoteDto> dtos) {
        this.dtos = dtos;
    }

    public List<NoteDto> getDtos() {
        return dtos;
    }

    
}