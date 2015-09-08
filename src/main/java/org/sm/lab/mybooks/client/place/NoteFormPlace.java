package org.sm.lab.mybooks.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import org.sm.lab.mybooks.shared.dto.NoteDto;

public class NoteFormPlace extends Place {
    
    private NoteDto noteDto = null;
    
    public NoteFormPlace() {
    }
    
    public NoteFormPlace(NoteDto noteDto) {
        this.noteDto = noteDto;
    }
    
    public NoteDto getNoteDto() {
        return this.noteDto;
    }
    
	public static class Tokenizer implements PlaceTokenizer<NoteFormPlace> {

		@Override
		public NoteFormPlace getPlace(String token) {
			return new NoteFormPlace();
		}

		@Override
		public String getToken(NoteFormPlace place) {
			return "";
		}

	}
}
