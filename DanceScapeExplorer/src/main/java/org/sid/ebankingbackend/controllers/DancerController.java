package org.sid.ebankingbackend.controllers;

import org.sid.ebankingbackend.entities.Dancer;
import org.sid.ebankingbackend.services.IDancerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



import java.util.List;


@RestController
@RequestMapping("/dancer")
@CrossOrigin("*")
public class DancerController {
    @Autowired
    IDancerservice dancserv;
    @GetMapping("/retrieve_all_dancers")
    public List<Dancer> getDancers() {
        List<Dancer> listdancers = dancserv.retrieveAllDancers();
        return listdancers;
    }

    @PostMapping("/add_dancer")
    public Dancer addDancer(@RequestBody Dancer d) {
        Dancer dancer = dancserv.addDancer(d);
        return dancer;
    }

    @PutMapping("/update_dancer/{id}")
    public Dancer  updateDancestyle(@PathVariable Long id, @RequestBody Dancer d) {

        d.setIddancer(id);
        Dancer dancer = dancserv.updateDancer(d);
        return dancer;
    }
    @GetMapping("/retrieve_dancer/{id}")
    public Dancer  retrieveDancer(@PathVariable Long id) {
        Dancer dancer = dancserv.retrieveDancer(id);
        return dancer;
    }

    @DeleteMapping("/remove_dancer/{id}")
    public void removeDancer(@PathVariable Long id) {
        dancserv.removeDancer(id);
    }
}
