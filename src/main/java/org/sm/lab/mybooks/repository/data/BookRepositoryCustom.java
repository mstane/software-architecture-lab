package org.sm.lab.mybooks.repository.data;

import org.sm.lab.mybooks.domain.SearchItem;
import org.sm.lab.mybooks.enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {

    public Page<SearchItem> searchContents(Long readerId, String keyword, Genre genre, Pageable pageable);

    public Long countSearch(Long readerId, String keyword, Genre genre);

}
