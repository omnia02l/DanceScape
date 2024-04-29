package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Competition;
import org.sid.ebankingbackend.entities.Dancecategory;
import org.sid.ebankingbackend.entities.Performance;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IPerformanceservice {

    Performance addPerformanceToTeamInCompetition(Long competitionId, Long teamId, Performance performance);
    Map<Competition, Set<Performance>> getPerformancesByCompetition();

      Performance retrieveperformance(Long id);







}
