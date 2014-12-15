package org.sm.lab.mybooks.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class BookListPlace extends Place {
	public static class Tokenizer implements PlaceTokenizer<BookListPlace> {

		@Override
		public BookListPlace getPlace(String token) {
			return new BookListPlace();
		}

		@Override
		public String getToken(BookListPlace place) {
			return "";
		}

	}
}
