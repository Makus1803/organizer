package com.organizer.domain.application;

import com.organizer.domain.application.commands.RegistrationCommand;
import com.organizer.domain.model.user.RegistrationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void register(RegistrationCommand command) throws RegistrationException;
}
