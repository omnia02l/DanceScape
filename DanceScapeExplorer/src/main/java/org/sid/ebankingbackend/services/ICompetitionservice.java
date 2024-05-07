package org.sid.ebankingbackend.services;




import org.sid.ebankingbackend.entities.Competition;
import org.sid.ebankingbackend.entities.GenderstatDTO;

import java.util.List;
import java.util.Map;


public interface ICompetitionservice {
    List<Competition> retrieveAllCompetitions();

    Competition addCompetition(Competition c);

    Competition updateCompetition(Competition c);

    Competition retrieveCompetition(Long idcomp);

    void removeCompetition(Long idcomp);
    Competition addCompetition(Competition c, Long categoryId, Long styleId,Long venueId);
    GenderstatDTO getGenderStatsForCompetition(Long competitionId);
    Map<String, Long> getCompetitionCountByDanceStyle();
    Map<String, Long> getNumberOfParticipantsPerCompetition();
    String getCountdown(Competition competition);
}
