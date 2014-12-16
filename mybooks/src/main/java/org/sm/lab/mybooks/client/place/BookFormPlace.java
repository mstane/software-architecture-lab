package org.sm.lab.mybooks.client.place;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

import org.sm.lab.mybooks.shared.dto.BookDto;

public class BookFormPlace extends Place {
    
    private BookDto bookDto = null;
    
    public BookFormPlace() {
    }
    
    public BookFormPlace(BookDto bookDto) {
        this.bookDto = bookDto;
    }
    
    public BookDto getBookDto() {
        return this.bookDto;
    }
    
	public static class Tokenizer implements PlaceTokenizer<BookFormPlace> {

		@Override
		public BookFormPlace getPlace(String token) {
			Log.debug("BookFormPlace.Tokenizer.getPlace");
			return new BookFormPlace();
		}

		@Override
		public String getToken(BookFormPlace place) {
			String token = "";
			if (place != null && place.getBookDto() != null && place.getBookDto().getId() != null) {
			    token = "id=" + place.getBookDto().getId();
			}
			Log.debug("BookFormPlace.Tokenizer.getToken;token=" + token);
		    return token;
		}

	}
}
