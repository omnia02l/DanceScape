package org.sid.ebankingbackend.services;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Dancecategory;
import org.sid.ebankingbackend.entities.Music;
import org.sid.ebankingbackend.repository.DancecategRepository;
import org.sid.ebankingbackend.repository.DancestyleRepository;
import org.sid.ebankingbackend.repository.MusicRepository;
import org.sid.ebankingbackend.services.IDancecategservice;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class Dancecategservice implements IDancecategservice {
    @Autowired
    DancecategRepository categrepo;
    @Autowired
    DancestyleRepository stylerepo;
    @Autowired
    MusicRepository musicrepo;
    @Override
    public List<Dancecategory> retrieveAllDancecategories() {return categrepo.findAll();}

    @Override
    public Dancecategory addDancecategory(Dancecategory dc) {return categrepo.save(dc);}

    @Override
    public Dancecategory updateDancecategory(Dancecategory dc) {return categrepo.save(dc);}

    @Override
    public Dancecategory retrieveDancecategory(Long idcateg) {return categrepo.findById(idcateg).get();}

    @Override
    public void removeDancecategory(Long idcateg) {categrepo.deleteById(idcateg);}



}



