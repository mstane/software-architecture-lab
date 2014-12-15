package org.sm.lab.mybooks.shared.dto;

import java.io.Serializable;
import java.util.Comparator;

public class BookDtoComparator implements Comparator<BookDto>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(BookDto o1, BookDto o2) {        
        if (o1 == o2) {
            return 0;
        }
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }

        // Rating has priority in ordering book list
        if (o1.getRating() == null && o2.getRating() == null) {
            if (o1.getTitle() == o2.getTitle()) {
                return 0;
            }
            if (o1.getTitle() == null) {
                return -1;
            }
            if (o2.getTitle() == null) {
                return 1;
            }
            return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
        } else {
            if (o1.getRating() == null) {
                return -1;
            }
            if (o2.getRating() == null) {
                return 1;
            }
            return o1.getRating().compareTo(o2.getRating());
        }
        
    }


}
