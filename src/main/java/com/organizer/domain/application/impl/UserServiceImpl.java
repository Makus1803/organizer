package com.organizer.domain.application.impl;

import com.organizer.domain.application.UserService;
import com.organizer.domain.application.commands.RegistrationCommand;
import com.organizer.domain.common.event.DomainEventPublisher;
import com.organizer.domain.common.mail.MailManager;
import com.organizer.domain.common.mail.MessageVariable;
import com.organizer.domain.model.user.RegistrationException;
import com.organizer.domain.model.user.RegistrationManagement;
import com.organizer.domain.model.user.User;
import com.organizer.domain.model.user.events.UserRegisteredEvent;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private RegistrationManagement registrationManagement;
    private DomainEventPublisher domainEventPublisher;
    private MailManager mailManager;

    public UserServiceImpl(RegistrationManagement registrationManagement, DomainEventPublisher domainEventPublisher,
                           MailManager mailManager) {
        this.registrationManagement = registrationManagement;
        this.domainEventPublisher = domainEventPublisher;
        this.mailManager = mailManager;
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
}
