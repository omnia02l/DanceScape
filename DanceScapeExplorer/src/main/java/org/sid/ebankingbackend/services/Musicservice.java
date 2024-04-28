package org.sid.ebankingbackend.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Music;
import org.sid.ebankingbackend.entities.Performance;
import org.sid.ebankingbackend.entities.Team;
import org.sid.ebankingbackend.repository.MusicRepository;
import org.sid.ebankingbackend.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class Musicservice implements IMusicService {
    @Autowired
    MusicRepository musicrepo;
    @Autowired
    PerformanceRepository perfrepo;

    @Override
    public List<Music> retrieveAllMusics() {
        return  musicrepo.findAll();
    }

    @Override
    public Music addmusic(Music m) {
        return musicrepo.save(m);
    }



    @Override
    public Music retrievemusic(Long id) {
        return musicrepo.findById(id).get();
    }

    @Override
    public void removemusic(Long id) {
        musicrepo.deleteById(id);
    }
    @Override
    public String recommendMusic(String danceCategory, String danceStyle) {
        Music music = musicrepo. findBydanceCategAndStyle(danceCategory, danceStyle);
        return music != null ? music.getMusicname() : null;
    }
    @Override
    @Transactional
    public void assignMusicToPerformance(Long musicId, Long performanceId) {
        Music music = musicrepo.findById(musicId).get();

        Performance performance = perfrepo.findById(performanceId).get();

        music.getPerformances().add(performance);
        performance.setMusic(music);

        musicrepo.save(music);
        perfrepo.save(performance);
    }
}
