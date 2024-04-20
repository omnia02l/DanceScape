package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import org.sid.ebankingbackend.entities.PurchaseTransaction;
import org.sid.ebankingbackend.services.Tickets.PurchaseTrandsactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/PurchaseTransaction")
public class PurchaseTransactionController  {
    @Autowired
    PurchaseTrandsactionService purchaseTrandsactionService;

    @GetMapping("/GetAllPurchaseTransaction")
    public List<PurchaseTransaction> GetAllPurchaseTransaction() {
        return purchaseTrandsactionService.GetAllPurchaseTransaction() ;
    }

    @GetMapping("/GetAllPurchaseTransaction/{id}")
    public PurchaseTransaction GetPurchaseTransaction(@PathVariable Long id) {
        return purchaseTrandsactionService.GetPurchaseTransaction(id);
    }

   @PostMapping("/AddPurchaseTransaction")
    public PurchaseTransaction AddPurchaseTransaction(@RequestBody PurchaseTransaction purchaseTransaction) {
        return purchaseTrandsactionService.AddPurchaseTransaction(purchaseTransaction);
    }

   @PutMapping("/ModifyPurchaseTransaction")
    public PurchaseTransaction ModifyPurchaseTransaction(@RequestBody PurchaseTransaction purchaseTransaction) {
        return purchaseTrandsactionService.ModifyPurchaseTransaction(purchaseTransaction);
    }

   @DeleteMapping("/DeletPurchaseTransaction/{id}")
    public void DeletPurchaseTransaction(@PathVariable Long id) {
            purchaseTrandsactionService.DeletPurchaseTransaction(id);
    }


}

