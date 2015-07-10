package org.sm.lab.mybooks3.enums;

import java.util.ArrayList;
import java.util.List;

public enum Genre {

    Comedy, Drama, Epic, Erotic, Lyric, Mythopoeia, Nonsense, Other, Romance, Satire, Tragedy, Tragicomedy;
    
    public static List<String> names() {

        List<String> list = new ArrayList<String>();
        for (Genre s : values()) {
            list.add(s.name());
        }

        return list;
    }
}
