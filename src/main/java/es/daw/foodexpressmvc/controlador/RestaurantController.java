package es.daw.foodexpressmvc.controlador;

import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    public String listRestaurants(Model model) {

        List<RestaurantDTO> restaurants = restaurantService.getRestaurants();
        model.addAttribute("restaurants",restaurants);
        return "restaurants/restaurants";
    }

    @GetMapping("/menu")
    public String restaurantMenu(Model model, Principal principal) {
        String username = principal.getName();

        model.addAttribute("usename", username);

        return "restaurants/restaurants-menu";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("usename", username);
        model.addAttribute("restaurant", new RestaurantDTO());
        return "restaurants/restaurant-create";
    }

    @PostMapping("/create")
    public String saveRestaurant(@ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
                                 Principal principal,
                                 Model model) {

        System.out.println("******restaurantDTO:"+restaurantDTO);

        RestaurantDTO saved = restaurantService.createRestaurant(restaurantDTO);
        // Pendiente!! pasar el restaurante y mostrarlo... se ha creado bien el resta....
        // Pendiente: añadir la cabecera (username)...

        return "restaurants/create-success";

    }

    @PostMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable("id") Long id) {

        return "";

    }




}
