package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.PlaceStatistics;
import org.sid.ebankingbackend.entities.Places;
import org.sid.ebankingbackend.entities.RowLabel;
import org.sid.ebankingbackend.entities.SeatStatusDTO;
import org.sid.ebankingbackend.repository.Tickets.PlacesRepository;
import org.sid.ebankingbackend.services.Tickets.PlacesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/Places")
public class PlacesController {
    @Autowired
    PlacesServices placesService;
    @Autowired
    PlacesRepository placeRepository;

    @GetMapping("/GetAllPlaces")
    public List<Places> GetAllPlaces() {
        return placesService.GetAllPlaces();
    }


    @GetMapping("/GetPlaces/{nb}")

    public Places GetPlaces(@PathVariable Long nb) {
        return placesService.GetPlaces(nb);
    }

   /* @PutMapping("/UpdatePlace/{id}")
    public Places updatePlaceOccupation(@PathVariable Long id, @RequestBody Places place) {
        return placesService.updatePlaceOccupation(id, place.isOccupied());
    }*/

    @PutMapping("/ModifyPlaces")

    public Places ModifyPlaces(@RequestBody Places places) {
        return placesService.ModifyPlaces(places);
    }


    @DeleteMapping("/DeletPlaces/{id}")

    public void DeletPlaces(@PathVariable Long id) {
        placesService.DeletPlaces(id);
    }

    @GetMapping("/seatNumbers/{planId}")
    public Map<String, List<Map<String, Object>>> getSeatNumbersByRow(@PathVariable Long planId) {
     return  placesService.getSeatNumbersByRow(planId);
    }



    @GetMapping("/findPlace")
    public Places findPlaceBySeatAndRow(@RequestParam Long seatNumber, @RequestParam RowLabel row,@RequestParam Long venuePlanId) {
        return  placesService.findPlaceBySeatAndRowAndVenuePlan(seatNumber,row,venuePlanId);
    }

    @PutMapping("/confirmPlaces/{userid}")
    public List<Places> confirmPlaces(@RequestParam Long venuePlanId,@PathVariable Long userid,@RequestBody List<Long> places) {
        return placesService.confirmPlaces(venuePlanId,userid,places);
    }


    @PutMapping("/togglePlaceSelection/{id}")
    public Places togglePlaceSelection(@PathVariable Long id) {
        return placesService.togglePlaceSelection(id);
    }


    @GetMapping("/places/statistics/{venuePlanId}")
    public List<SeatStatusDTO> getSeatStatistics(@PathVariable Long venuePlanId) {
        return placesService.getPlaceStatistics(venuePlanId);
    }
}
