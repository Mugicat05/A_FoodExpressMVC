package es.daw.foodexpressmvc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConnectApiRestException.class)
    public String handleConnectApiRestException(ConnectApiRestException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "api-error";

    }
}
