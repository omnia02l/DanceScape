package org.sid.ebankingbackend.services.Tickets;


import org.sid.ebankingbackend.entities.PurchaseTransaction;

import java.util.List;

public interface IPurchaseTransactionService {
    public List<PurchaseTransaction> GetAllPurchaseTransaction();
    public PurchaseTransaction GetPurchaseTransaction(Long id);
    public PurchaseTransaction AddPurchaseTransaction(PurchaseTransaction purchaseTransaction);
    public PurchaseTransaction ModifyPurchaseTransaction(PurchaseTransaction purchaseTransaction);
    public void DeletPurchaseTransaction(Long id);

}
