package org.sm.lab.mybooks.client.place;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BookListPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<BookListPlace> {

		@Override
		public BookListPlace getPlace(String token) {
			Log.debug("BookListPlace.Tokenizer.getPlace");
			return new BookListPlace();
		}

		@Override
		public String getToken(BookListPlace place) {
			Log.debug("BookListPlace.Tokenizer.getToken");
			return "";
		}

	}
}
