package org.sm.lab.mybooks.shared.dto;

public enum Genre {
    Comedy(0), Drama(1), Epic(2), Erotic(3), Lyric(4), Mythopoeia(5), Nonsense(6), Other(7), Romance(8), Satire(9), Tragedy(10), Tragicomedy(11);
    
    private Integer value;
    
    private Genre(Integer value) {
        this.value = value;
    }
    
    public Integer getValue() {
        return this.value;
    }
}
