package org.sid.ebankingbackend.services;
import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Dancer;
import org.sid.ebankingbackend.repository.DancerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class Dancerservice implements IDancerservice {
    @Autowired
    DancerRepository dancerepo;
    @Override
    public List<Dancer> retrieveAllDancers() {return dancerepo.findAll();}

    @Override
    public Dancer addDancer(Dancer d) {return dancerepo.save(d);}

    @Override
    public Dancer updateDancer(Dancer d) {return dancerepo.save(d);}

    @Override
    public Dancer retrieveDancer(Long iddancer) {return dancerepo.findById(iddancer).get();}

    @Override
    public void removeDancer(Long iddancer) {dancerepo.deleteById(iddancer);}


    }

