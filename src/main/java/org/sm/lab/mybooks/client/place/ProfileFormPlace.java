package org.sm.lab.mybooks.client.place;

import org.sm.lab.mybooks.shared.dto.ReaderDto;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ProfileFormPlace extends Place {
    
    private ReaderDto readerDto = null;
    
    public ProfileFormPlace() {
    }
    
    public ProfileFormPlace(ReaderDto readerDto) {
        this.readerDto = readerDto;
    }
    
    public ReaderDto getReaderDto() {
        return this.readerDto;
    }
    
	public static class Tokenizer implements PlaceTokenizer<ProfileFormPlace> {

		@Override
		public ProfileFormPlace getPlace(String token) {
			Log.debug("ProfileFormPlace.Tokenizer.getPlace");
			return new ProfileFormPlace();
		}

		@Override
		public String getToken(ProfileFormPlace place) {
			Log.debug("ProfileFormPlace.Tokenizer.getToken");
			return "";
		}

	}
}
