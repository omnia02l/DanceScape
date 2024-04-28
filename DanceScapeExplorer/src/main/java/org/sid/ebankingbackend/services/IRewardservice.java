package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Reward;

import java.util.List;



public interface IRewardservice {
    List<Reward> retrieveAllrewards();

    Reward addreward(Reward r);


    Reward retrievereward(Long id);

    void removereward(Long id);

}
