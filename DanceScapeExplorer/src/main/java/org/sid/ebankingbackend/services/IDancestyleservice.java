package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Dancecategory;
import org.sid.ebankingbackend.entities.Dancestyle;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Set;

public interface IDancestyleservice {
    List<Dancestyle> retrieveAllDancestyles();

    Dancestyle addDancestyle(Dancestyle ds);

    Dancestyle updateDancestyle(Dancestyle ds);

    Dancestyle retrieveDancestyle(Long ids);

    void removeDancestyle(Long ids);
     Dancecategory addDanceStyleToCategory(Long id, Dancestyle dancestyle);
    Dancecategory removeDanceStyleFromCategory(Long categoryId, Long danceStyleId);
    Set<Dancestyle> getStylesByCategoryId(Long categoryId);
    public List<String> getAllDistinctStyledNames();



}
