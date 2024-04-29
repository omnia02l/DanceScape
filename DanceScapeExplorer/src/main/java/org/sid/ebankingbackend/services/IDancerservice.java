

package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.Dancer;

import java.util.Date;
import java.util.List;

public interface IDancerservice {
    List<Dancer> retrieveAllDancers();

    Dancer addDancer(Dancer d);

    Dancer updateDancer(Dancer d);

    Dancer retrieveDancer(Long iddancer);

    void removeDancer(Long iddancer);


}
