package org.sid.ebankingbackend.services.Tickets;



import org.sid.ebankingbackend.entities.VenuePlan;

import java.util.List;

public interface IVenuePlanService {
    public List<VenuePlan> GetAllTheatrePlan();
    public void GetTheatrePlan(Long id);
    public VenuePlan addTheatrePlanWithSeats(VenuePlan plan, Long venueId);
    public VenuePlan ModifyTheatrePlan(VenuePlan plan);
    public void DeletTheatrePlan(Long id);

}
