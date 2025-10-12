package fr.isencaen.api_what_time.service;

import fr.isencaen.api_what_time.controller.Dto.LocationDto;
import fr.isencaen.api_what_time.repository.Entity.Location;
import fr.isencaen.api_what_time.repository.LocationRepository;
import fr.isencaen.api_what_time.service.Model.LocationModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public List<LocationModel> getAllLocations(){
        List<Location> locs = locationRepository.findAll() ;
        List<LocationModel> locsModels = new ArrayList<>();
        for (Location loc : locs){
            if (!loc.isArchived()){
                locsModels.add(LocationModel.of(loc));
            }
        }
        return locsModels;
    }

    public LocationModel getLocationById(int id) {
        try{
            Location loc = locationRepository.findById(id).orElseThrow();
            if (loc.isArchived()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }
            return LocationModel.of(loc);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public LocationModel createLocation(LocationDto locDto){
        String name = locDto.name();
        Double latitude = locDto.longitude();
        Double longitude = locDto.latitude();
        String description = locDto.description();

        if (name == null || latitude == null || longitude == null || description == null) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        name = name.strip();
        description = description.strip();
        if (name.isBlank()) throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

        Location loc = new Location(name, longitude, latitude, description);
        locationRepository.save(loc);
        return LocationModel.of(loc);
    }

    @Transactional
    public LocationModel updateLocation(int id, LocationDto locDto){
        Location loc;
        try{
            loc = locationRepository.findById(id).orElseThrow();
            if (loc.isArchived()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
            }
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        String name = locDto.name();
        Double latitude = locDto.longitude();
        Double longitude = locDto.latitude();
        String description = locDto.description();

        if (name!=null){
            name = name.strip();
            if (!name.isBlank()) loc.setName(name);
        }
        if (latitude != null) loc.setLatitude(latitude);
        if (longitude != null) loc.setLongitude(longitude);
        if (description != null) loc.setDescription(description);

        return LocationModel.of(loc);
    }

    @Transactional
    public LocationModel deleteLocation(int id) {
        try{
            Location loc = locationRepository.findById(id).orElseThrow();
            if (loc.isArchived()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            loc.setArchived(true);
            return LocationModel.of(loc);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
