package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Dancecategory;
import org.sid.ebankingbackend.entities.Dancestyle;
import org.sid.ebankingbackend.entities.Music;
import org.sid.ebankingbackend.entities.Reward;
import org.sid.ebankingbackend.services.IDancecategservice;
import org.sid.ebankingbackend.services.IDancestyleservice;
import org.sid.ebankingbackend.services.IMusicService;
import org.sid.ebankingbackend.services.IRewardservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/music")
@CrossOrigin("*")
public class Musiccontroller {
    @Autowired
    IDancecategservice categserv;
    @Autowired
    IDancestyleservice styleserv;
    @Autowired
    IMusicService musicserv;


    @GetMapping("/recommendMusic")
    public String recommendMusic(@RequestParam String danceCategory, @RequestParam String danceStyle) {
        return musicserv.recommendMusic(danceCategory, danceStyle);
    }


    @GetMapping("/retrieve_all_Music")
    public List<Music> getTMusics() {
        List<Music> list = musicserv.retrieveAllMusics();
        return list;
    }

    @PostMapping("/add_Music")
    public Music addMusic(@RequestBody Music m) {
        Music music = musicserv.addmusic(m);
        return music;
    }

    @PutMapping("/update_Music/{id}")
    public Music  updateMusic(@PathVariable Long id, @RequestBody Music m) {

        m.setIdmusic(id);
        Music mu = musicserv.addmusic(m);
        return mu;
    }
    @GetMapping("/retrieve_Music/{id}")
    public Music  retrieveMusic(@PathVariable Long id) {
        Music mu = musicserv.retrievemusic(id);
        return mu;
    }

    @DeleteMapping("/remove_Music/{id}")
    public void removeMusic(@PathVariable Long id) {
        musicserv.removemusic(id);
    }


    @PostMapping("/assignmusictoperf")
    public void assignMusicToPerformance(@RequestParam Long musicId, @RequestParam Long performanceId) {
        musicserv.assignMusicToPerformance(musicId, performanceId);
    }




}
