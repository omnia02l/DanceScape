package org.sid.ebankingbackend.controllers.Tickets;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebankingbackend.entities.PurchaseTransaction;
import org.sid.ebankingbackend.entities.Ticket;
import org.sid.ebankingbackend.services.Tickets.PurchaseTrandsactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/PurchaseTransaction")
public class PurchaseTransactionController  {
    @Autowired
    PurchaseTrandsactionService purchaseTrandsactionService;


/*@PostMapping("/upload")
public ResponseEntity<List<String>> uploadAndExtract(@RequestParam("file") MultipartFile file) {
    try {
        List<String> qrData = purchaseTrandsactionService.processPDF(file);
        if (qrData.isEmpty()) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
        return ResponseEntity.ok(qrData);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body(new ArrayList<>());
    }
}*/
@PostMapping("/upload")
public ResponseEntity<List<Ticket>> uploadAndExtract(@RequestParam("file") MultipartFile file) {
    try {
        List<Ticket> tickets = purchaseTrandsactionService.processPDF(file);
        if (tickets.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tickets);
    } catch (Exception e) {
        log.error("Error processing PDF", e);
        return ResponseEntity.internalServerError().build();
    }
}

@GetMapping("/checkTicketScanned")
    private boolean checkTicketScanned(@RequestParam String ticketReference){
    return purchaseTrandsactionService.checkTicketScanned(ticketReference);
    }

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

