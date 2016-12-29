package org.sm.lab.mybooks.enums;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

public enum Genre {

    COMEDY, DRAMA, EPIC, EROTIC, LYRIC, MYTHOPOEIA, NONSENSE, OTHER, ROMANCE, SATIRE, TRAGEDY, TRAGICOMEDY;

    public static List<String> names() {
        return Arrays.stream(values()).map(Genre::name).collect(toList());
    }
}
