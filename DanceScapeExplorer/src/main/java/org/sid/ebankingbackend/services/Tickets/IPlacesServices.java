package org.sid.ebankingbackend.services.Tickets;


import org.sid.ebankingbackend.entities.Places;

import java.util.List;

public interface IPlacesServices {
    public List<Places> GetAllPlaces();
    public Places GetPlaces(Long nb);

    public Places ModifyPlaces(Places places);
    public void DeletPlaces(Long id);

}
