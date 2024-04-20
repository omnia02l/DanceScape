package org.sid.ebankingbackend.services.Tickets;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.sid.ebankingbackend.entities.PurchaseTransaction;
import org.sid.ebankingbackend.repository.Tickets.PurchaseTransactionRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class PurchaseTrandsactionService implements IPurchaseTransactionService{

 PurchaseTransactionRepository purchaseTransactionRepository;

    @Override
    public List<PurchaseTransaction> GetAllPurchaseTransaction() {
        return purchaseTransactionRepository.findAll();
    }

    @Override
    public PurchaseTransaction GetPurchaseTransaction(Long id) {
        return purchaseTransactionRepository.findById(id).get();
    }

    @Override
    public PurchaseTransaction AddPurchaseTransaction(PurchaseTransaction purchaseTransaction) {
        return purchaseTransactionRepository.save(purchaseTransaction);
    }

    @Override
    public PurchaseTransaction ModifyPurchaseTransaction(PurchaseTransaction purchaseTransaction) {
        return purchaseTransactionRepository.save(purchaseTransaction);
    }

    @Override
    public void DeletPurchaseTransaction(Long id) {
        purchaseTransactionRepository.deleteById(id);

    }


}


