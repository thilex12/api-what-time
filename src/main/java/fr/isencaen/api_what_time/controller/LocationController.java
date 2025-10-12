package fr.isencaen.api_what_time.controller;

import fr.isencaen.api_what_time.controller.Dto.LocationDto;
import fr.isencaen.api_what_time.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("v1/locations")
    public List<LocationDto> getLocations() {
        return locationService.getAllLocations().stream().map(LocationDto::of).toList();
    }

    @GetMapping("v1/locations/{locationId}")
    public LocationDto getLocation(@PathVariable Integer locationId){
        return LocationDto.of(locationService.getLocationById(locationId));
    }

    @PostMapping("v1/locations")
    public LocationDto newLocation(@RequestBody LocationDto locDto){
        return LocationDto.of(locationService.createLocation(locDto));
    }

    @PutMapping("v1/locations/{locationId}")
    public LocationDto updateLocation(@PathVariable Integer locationId, @RequestBody LocationDto locDto){
        return LocationDto.of(locationService.updateLocation(locationId, locDto));
    }

    @DeleteMapping("v1/locations/{locationId}")
    public LocationDto deleteLocation(@PathVariable Integer locationId){
        return LocationDto.of(locationService.deleteLocation(locationId));
    }

}
