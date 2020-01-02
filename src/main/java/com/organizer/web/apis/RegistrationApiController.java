package com.organizer.web.apis;

import com.organizer.domain.application.UserService;
import com.organizer.domain.model.user.EmailAddressExistsException;
import com.organizer.domain.model.user.RegistrationException;
import com.organizer.domain.model.user.UsernameExistsException;
import com.organizer.web.payload.RegistrationPayload;
import com.organizer.web.results.ApiResult;
import com.organizer.web.results.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class RegistrationApiController {

    private UserService service;

    public RegistrationApiController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/registrations")
    public ResponseEntity<ApiResult> register(
            @Valid @RequestBody RegistrationPayload payload) {
        try {
            service.register(payload.toCommand());
            return Result.created();
        } catch (RegistrationException e){
            String errorMessage = "Registration failed";
            if(e instanceof UsernameExistsException){
                errorMessage = "Username already exists";
            }
            if(e instanceof EmailAddressExistsException){
                errorMessage = "Email Address already exists";
            }
            return Result.failure(errorMessage);
        }

    }
}
