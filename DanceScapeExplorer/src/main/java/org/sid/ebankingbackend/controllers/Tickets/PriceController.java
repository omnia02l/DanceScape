package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.Price;
import org.sid.ebankingbackend.entities.TrancheAge;
import org.sid.ebankingbackend.entities.TypeTicket;
import org.sid.ebankingbackend.services.Tickets.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/Price")
public class PriceController  {
    @Autowired
    PriceService priceService;

    @GetMapping("/GetAllPrice")
    public List<Price> GetAllPrice() {
        return priceService.GetAllPrice();
    }

    @GetMapping("/GetPrice/{typeTicket}/{trancheAge}")
    public Price GetPrice(@PathVariable TypeTicket typeTicket, @PathVariable TrancheAge trancheAge) {
        return priceService.GetPrice(typeTicket,trancheAge);
    }


   @PostMapping("/AddPrice")
    public Price AddPrice(@RequestBody Price price) {
        return priceService.AddPrice(price);
    }

    @PutMapping("/ModifyPrice")
    public Price ModifyPrice(@RequestBody Price price) {
        return priceService.ModifyPrice(price);
    }

   @DeleteMapping("/DeletPrice/{id}")
    public void DeletPrice(@PathVariable Long id) {
        priceService.DeletPrice(id);
    }

    @GetMapping("/GetPriceById/{id}")
    public Price GetPricebyID(Long id) {
        return priceService.GetPricebyID(id);
    }
}