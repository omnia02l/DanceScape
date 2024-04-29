package org.sid.ebankingbackend.services.Tickets;


import org.sid.ebankingbackend.entities.Price;
import org.sid.ebankingbackend.entities.TrancheAge;
import org.sid.ebankingbackend.entities.TypeTicket;

import java.util.List;

public interface IPriceService {
    public List<Price> GetAllPrice();
    public Price GetPrice(TypeTicket typeTicket , TrancheAge trancheAge);
    public Price GetPricebyID(Long id);
    public Price AddPrice(Price price);
    public Price ModifyPrice(Price price);
    public void DeletPrice(Long id);

;
}
