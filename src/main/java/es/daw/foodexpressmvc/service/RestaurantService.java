package es.daw.foodexpressmvc.service;

import es.daw.foodexpressmvc.dto.RestaurantDTO;
import es.daw.foodexpressmvc.exception.ConnectApiRestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final WebClient webClientAPI;

    public List<RestaurantDTO> getRestaurants() {

        RestaurantDTO[] restaurants;

        try {
            restaurants = webClientAPI
                    .get()
                    .uri("/restaurants")
                    .retrieve() // verifica el status. Si 4xx o 5xx, lanza error. Si ex 2xx continúa
                    .bodyToMono(RestaurantDTO[].class)
                    .block(); // Bloquea y espera. Síncrono
        }catch (Exception e){
            //throw new ConnectApiRestException("Could not connect to FoodExpres API");
            throw new ConnectApiRestException(e.getMessage());
        }

        //return List.of(restaurants);
        return Arrays.asList(restaurants);

    }

    /**
     *
     * @param restaurantDTO
     * @return
     */
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        // PENDIENTE CREAR ENVIANDO EL TOKEN!!!!!
        RestaurantDTO dto;

        // Pendiente usar el servicio de Auth para obtener el token...
        try {
            dto = webClientAPI
                    .post()
                    .uri("/restaurants")
                    //.header()
                    .bodyValue(restaurantDTO)
                    .retrieve()
                    .bodyToMono(RestaurantDTO.class)
                    .block(); // Bloquea y espera. Síncrono
        }catch (Exception e){
            //throw new ConnectApiRestException("Could not connect to FoodExpres API");
            throw new ConnectApiRestException(e.getMessage());
        }

        // Pendiente usar Optional!!!
        return dto;
    }
}
