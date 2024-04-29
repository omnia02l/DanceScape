package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.VenuePlan;
import org.sid.ebankingbackend.services.Tickets.VenuePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/VenuePlan")

public class VenuePlanController {


@Autowired
VenuePlanService VenueService;


    @CrossOrigin(origins = "http://localhost:4200")

    @GetMapping("/GetAllTheathrePlan")

    public List<VenuePlan> GetAllTheatrePlan() {
        return VenueService.GetAllTheatrePlan();
    }
    @GetMapping("/GetTheathrePlan/{id}")

    public void GetTheatrePlan(@PathVariable Long id) {
        VenueService.GetTheatrePlan(id);
    }


    @PostMapping("/AddTheatrePlan")

    public VenuePlan addTheatrePlanWithSeats(@RequestBody VenuePlan plan, @RequestParam Long venueId){
        return VenueService.addTheatrePlanWithSeats(plan,venueId);
    }



    @PutMapping("/ModifyTheatrePlan")

    public VenuePlan ModifyTheatrePlan(@RequestBody VenuePlan plan) {
        return  VenueService.ModifyTheatrePlan(plan);
    }
    @DeleteMapping("/DeletTheatrePlan/{id}")

    public void DeletTheatrePlan(@PathVariable Long id) {
        VenueService.DeletTheatrePlan(id);
    }




}
