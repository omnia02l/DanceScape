package org.sid.ebankingbackend.services;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Dancecategory;
import org.sid.ebankingbackend.entities.Dancestyle;
import org.sid.ebankingbackend.repository.DancecategRepository;
import org.sid.ebankingbackend.repository.DancestyleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class Dancestyleservice implements IDancestyleservice {
    @Autowired
    DancestyleRepository stylerepo;
    @Autowired
    DancecategRepository categrepo;
    @Override
    public List<Dancestyle> retrieveAllDancestyles() {
        return stylerepo.findAll();
    }

    @Override
    public Dancestyle addDancestyle(Dancestyle ds) {
        return stylerepo.save(ds);
    }

    @Override
    public Dancestyle updateDancestyle(Dancestyle ds) {
        return stylerepo.save(ds);
    }

    @Override
    public Dancestyle retrieveDancestyle(Long ids) {
        return stylerepo.findById(ids).get();
    }

    @Override
    public void removeDancestyle(Long ids) {
        stylerepo.deleteById(ids);

    }
    @Override
    public Dancecategory addDanceStyleToCategory(Long id, Dancestyle dancestyle) {
        // Recherche de la catégorie de danse par id
        Dancecategory category = categrepo.findById(id).get();


        // Ajout du nouveau style de danse à la liste existante de styles de danse de la catégorie
        category.getDancestyles().add(dancestyle);

        // Mise à jour de la catégorie de danse dans la base de données
        return categrepo.save(category);
    }

    @Override
    public Dancecategory removeDanceStyleFromCategory(Long categoryId, Long danceStyleId) {
        // Recherche de la catégorie de danse par id
        Dancecategory category = categrepo.findById(categoryId).get();

        // Recherche du style de danse par id
        Dancestyle dancestyle = stylerepo.findById(danceStyleId).get();

        // Retrait du style de danse de la liste existante de styles de danse de la catégorie
        category.getDancestyles().remove(dancestyle);

        // Mise à jour de la catégorie de danse dans la base de données
        return categrepo.save(category);
    }
    @Override
    public Set<Dancestyle> getStylesByCategoryId(Long categoryId) {
        Dancecategory dancecategory = categrepo.findById(categoryId).get();

            return dancecategory.getDancestyles();

    }
    @Override
    public List<String> getAllDistinctStyledNames() {
        return stylerepo.findAllDistinctStyledNames();
    }
}
