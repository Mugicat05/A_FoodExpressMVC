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
    private final ApiAuthService  apiAuthService;


    /**
     * Es público
     * @return
     */
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
     * Necesita jwt
     * @param restaurantDTO
     * @return
     */
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {

        System.out.println("********createRestaurant.........");
        RestaurantDTO dto;

        String token = apiAuthService.getToken();

        System.out.println("*** Token: " + token);

        try {
            dto = webClientAPI
                    .post()
                    .uri("/restaurants")
                    .header("Authorization", "Bearer " + token)
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

    public void delete(Long id) {


        String token = apiAuthService.getToken();

        try {
            webClientAPI
                    .delete()
                    .uri("/restaurants/{id}",id)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block(); // Bloquea y espera. Síncrono
        }catch (Exception e){
            //throw new ConnectApiRestException("Could not connect to FoodExpres API");
            throw new ConnectApiRestException(e.getMessage());
        }

    }
}
