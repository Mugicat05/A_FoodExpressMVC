package es.daw.foodexpressmvc.controlador;

import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

//    @GetMapping("/menu")
//    public String restaurantMenu(Model model, Principal principal) {
//        String username = principal.getName();
//
//        model.addAttribute("usename", username);
//
//        return "restaurants/restaurants-menu";
//    }

//    @GetMapping("/menu")
//    public String restaurantMenu(Model model) {
//        return "restaurants/restaurants-menu";
//    }


    @GetMapping("/create")
    public String showCreateForm(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("usename", username);
        model.addAttribute("restaurant", new RestaurantDTO());
        model.addAttribute("mode","create");
        return "restaurants/restaurant-form";
    }

    @PostMapping("/create")
    //public String saveRestaurant(@Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
    public String saveRestaurant(@ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
                                 Principal principal,
                                 Model model) {

        System.out.println("******restaurantDTO:"+restaurantDTO);

        try {
            RestaurantDTO saved = restaurantService.createRestaurant(restaurantDTO);
            // Pendiente!! pasar el restaurante y mostrarlo... se ha creado bien el resta....
            // Pendiente: añadir la cabecera (username)...
            return "restaurants/create-success";
        }catch (Exception e){
            model.addAttribute("errorMessage",e.getMessage());
            return "restaurants/restaurant-form";
        }

    }

    @PostMapping("/delete/{id}")
    public String deleteRestaurant(@PathVariable("id") Long id) {
        restaurantService.delete(id);
        return "redirect:/restaurants"; //llamar al endpoint

    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("mode","update");
        model.addAttribute("restaurant", restaurantService.getRestaurantById(id));
        return "restaurants/restaurant-form";

    }

    // CAUSA DE POR QUÉ NO SE VEÍA EL MENSAJE DE ERROR DEL TELÉFONO EN EL CAMPO DEL FORMULARIO:
    // Thymeleaf espera que en el modelo haya un atributo exactamente llamado restaurant, y sobre ese atributo aplicará los errores de validación.
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         //@Valid @ModelAttribute RestaurantDTO dto,
                         @Valid @ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
                         BindingResult bindingResult,
                         Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("mode","update");
            //model.addAttribute("restaurant", dto);
            return "restaurants/restaurant-form";
        }

        //restaurantService.updateRestaurant(id, dto);
        restaurantService.updateRestaurant(id, restaurantDTO);
        return "redirect:/restaurants"; //endpoint

    }



}
