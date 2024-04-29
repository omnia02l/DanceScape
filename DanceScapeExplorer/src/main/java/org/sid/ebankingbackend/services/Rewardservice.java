package org.sid.ebankingbackend.services;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Reward;
import org.sid.ebankingbackend.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class Rewardservice implements IRewardservice {
    @Autowired
    RewardRepository rewardrepo;

    @Override
    public List<Reward> retrieveAllrewards() {
        return  rewardrepo.findAll();
    }

    @Override
    public Reward addreward(Reward r) {
        return rewardrepo.save(r);
    }

    @Override
    public Reward retrievereward(Long id) {
        return rewardrepo.findById(id).get();
    }


    @Override
    public void removereward(Long id) {
        rewardrepo.deleteById(id);
    }
}
