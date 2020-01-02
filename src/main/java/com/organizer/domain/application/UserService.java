package com.organizer.domain.application;

import com.organizer.domain.application.commands.RegistrationCommand;
import com.organizer.domain.model.user.RegistrationException;


public interface UserService {
    void register(RegistrationCommand command) throws RegistrationException;
}
