package org.sm.lab.mybooks.shared.action;

import net.customware.gwt.dispatch.shared.Action;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class GetNoteAction implements Action<GetNoteResult> {

	private NoteDto dto;
	
    GetNoteAction() {
    }
    
    public GetNoteAction(NoteDto dto) {
    	this.dto = dto;
    }

    public NoteDto getDto() {
    	return this.dto;
    }
    
}