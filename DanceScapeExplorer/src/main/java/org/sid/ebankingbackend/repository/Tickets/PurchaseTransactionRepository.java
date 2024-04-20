package org.sid.ebankingbackend.repository.Tickets;

import org.sid.ebankingbackend.entities.PurchaseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransaction,Long> {

}
