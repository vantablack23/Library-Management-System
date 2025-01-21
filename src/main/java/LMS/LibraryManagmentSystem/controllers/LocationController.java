package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.Models.LocationModel;
import LMS.LibraryManagmentSystem.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/addLocation")
    public String addLocation(@ModelAttribute LocationModel newLocation, RedirectAttributes redirectAttributes){
        locationService.saveLocation(newLocation);

        redirectAttributes.addFlashAttribute("errorLocation", "Successfully added location");

        return "redirect:/profile";
    }
}
