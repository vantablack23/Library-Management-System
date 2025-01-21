package LMS.LibraryManagmentSystem.services;

import LMS.LibraryManagmentSystem.Models.LocationModel;
import LMS.LibraryManagmentSystem.entity.Location;
import LMS.LibraryManagmentSystem.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public void saveLocation(LocationModel locationModel){
        Location newLocation = new Location();
        newLocation.setLocation(locationModel.getLocation());

        locationRepository.save(newLocation);
    }
}
