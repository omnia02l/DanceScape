package org.sid.ebankingbackend.services;


import org.sid.ebankingbackend.entities.Dancecategory;


import java.util.List;
import java.util.Set;

public interface IDancecategservice {
    List<Dancecategory> retrieveAllDancecategories();

    Dancecategory addDancecategory(Dancecategory dc);

    Dancecategory updateDancecategory(Dancecategory dc);

    Dancecategory retrieveDancecategory(Long idcateg);

    void removeDancecategory(Long idcateg);


}
