package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Music;
import org.sid.ebankingbackend.entities.Team;

import java.util.List;

public interface IMusicService {
    List<Music> retrieveAllMusics();

    Music addmusic(Music m);


    Music retrievemusic(Long id);

    void removemusic(Long id);
    String recommendMusic(String danceCategory, String danceStyle);
    void assignMusicToPerformance(Long musicId, Long performanceId);
}
