package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.repository.Entity.Location;
import fr.isencaen.api_what_time.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public Location getLocationById(int id) {
        return locationRepository.findById(id).orElseThrow();
    }

}
