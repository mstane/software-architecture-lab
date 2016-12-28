package org.sm.lab.mybooks.enums;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;;

public enum Genre {

    Comedy, Drama, Epic, Erotic, Lyric, Mythopoeia, Nonsense, Other, Romance, Satire, Tragedy, Tragicomedy;
    
    public static List<String> names() {
    	return Arrays.stream(values())
    			.map(Genre::name)
    			.collect(toList());
    }
}
