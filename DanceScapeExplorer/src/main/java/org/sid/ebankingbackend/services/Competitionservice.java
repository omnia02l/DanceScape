package org.sid.ebankingbackend.services;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Competition;
import org.sid.ebankingbackend.entities.Dancecategory;
import org.sid.ebankingbackend.entities.Dancestyle;
import org.sid.ebankingbackend.entities.GenderstatDTO;
import org.sid.ebankingbackend.repository.CompetitionRepository;
import org.sid.ebankingbackend.repository.DancecategRepository;
import org.sid.ebankingbackend.repository.DancerRepository;
import org.sid.ebankingbackend.repository.DancestyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class Competitionservice implements ICompetitionservice {
    @Autowired
    CompetitionRepository comprepo;
    @Autowired
    DancestyleRepository stylrepo;
    @Autowired
    DancecategRepository categrepo;
    @Autowired
    DancerRepository dancerepo;

    @Override
    public List<Competition> retrieveAllCompetitions() {
        return comprepo.findAll();
    }

    @Override
    public Competition addCompetition(Competition c) {
        return comprepo.save(c);
    }

    @Override
    public Competition updateCompetition(Competition c) {
        return comprepo.save(c);
    }

    @Override
    public Competition retrieveCompetition(Long idcomp) {
        return comprepo.findById(idcomp).get();
    }

    @Override
    public void removeCompetition(Long idcomp) {
        comprepo.deleteById(idcomp);
    }

    @Override
    public Competition addCompetition(Competition c, Long categoryId, Long styleId) {
        // Récupération de la catégorie de danse sélectionnée
        Dancecategory category = categrepo.findById(categoryId).get();

        // Récupération du style de danse sélectionné
        Dancestyle style = stylrepo.findById(styleId).get();

        // Assignation de la catégorie et du style à la compétition
        c.setDancecateg(category);
        c.setStyle(style.getStyledname());

        // Enregistrement de la compétition
        return comprepo.save(c);
    }

    @Override
    public GenderstatDTO getGenderStatsForCompetition(Long competitionId) {
        // Récupérer le nombre total d'hommes pour la compétition
        int totalMaleDancers = dancerepo.countMaleDancersInTeamsInCompetition(competitionId);

        // Récupérer le nombre total de femmes pour la compétition
        int totalFemaleDancers = dancerepo.countFemaleDancersInTeamsInCompetition(competitionId);

        // Créer un objet GenderstatDTO avec les statistiques obtenues
        GenderstatDTO genderStats = new GenderstatDTO(totalMaleDancers, totalFemaleDancers);

        return genderStats;
    }
}






