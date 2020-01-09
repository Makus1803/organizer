package com.organizer.domain.application.impl;

import com.organizer.domain.application.UserService;
import com.organizer.domain.application.commands.RegistrationCommand;
import com.organizer.domain.common.event.DomainEventPublisher;
import com.organizer.domain.common.mail.MailManager;
import com.organizer.domain.common.mail.MessageVariable;
import com.organizer.domain.model.user.*;
import com.organizer.domain.model.user.events.UserRegisteredEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private RegistrationManagement registrationManagement;
    private DomainEventPublisher domainEventPublisher;
    private MailManager mailManager;
    private UserRepository userRepository;

    public UserServiceImpl(RegistrationManagement registrationManagement, DomainEventPublisher domainEventPublisher,
                           MailManager mailManager, UserRepository userRepository) {
        this.registrationManagement = registrationManagement;
        this.domainEventPublisher = domainEventPublisher;
        this.mailManager = mailManager;
        this.userRepository = userRepository;
    }

    @Override
    public void register(RegistrationCommand command) throws RegistrationException {
        Assert.notNull(command, "Parameter `command` must not be null");
        User newUser = registrationManagement.register(
                command.getUsername(),
                command.getEmailAddress(),
                command.getPassword());

                sendWelcomeMessage(newUser);
                domainEventPublisher.publish(new UserRegisteredEvent(newUser));
    }

    private void sendWelcomeMessage(User user){
        mailManager.send(
                user.getEmailAddress(),
                "Witaj w Organizer",
                "Witaj.ftl",
                MessageVariable.from("user", user)
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(username)){
            throw new UsernameNotFoundException("No user found");
        }
        User user;
        if (username.contains("@")) {
            user = userRepository.findByEmailAddress(username);
        } else {
            user = userRepository.findByUsername(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        return new SimpleUser(user);
    }
}
