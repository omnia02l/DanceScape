package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Reward;
import org.sid.ebankingbackend.entities.Team;
import org.sid.ebankingbackend.services.IRewardservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/reward")
@CrossOrigin("*")
public class Rewardcontroller {
    @Autowired
    IRewardservice rewardserv;
    @GetMapping("/retrieve_all_rewards")
    public List<Reward> getTrewards() {
        List<Reward> list = rewardserv.retrieveAllrewards();
        return list;
    }

    @PostMapping("/add_Reward")
    public Reward addReward(@RequestBody Reward R) {
        Reward reward = rewardserv.addreward(R);
        return reward;
    }

    @PutMapping("/update_reward/{id}")
    public Reward  updateReward(@PathVariable Long id, @RequestBody Reward r) {

        r.setIdreward(id);
        Reward re = rewardserv.addreward(r);
        return re;
    }
    @GetMapping("/retrieve_Reward/{id}")
    public Reward  retrieveReward(@PathVariable Long id) {
        Reward re = rewardserv.retrievereward(id);
        return re;
    }

    @DeleteMapping("/remove_Reward/{id}")
    public void removeReward(@PathVariable Long id) {
        rewardserv.removereward(id);
    }







}
