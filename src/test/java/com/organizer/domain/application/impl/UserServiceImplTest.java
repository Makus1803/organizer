package com.organizer.domain.application.impl;

import com.organizer.domain.application.commands.RegistrationCommand;
import com.organizer.domain.common.event.DomainEventPublisher;
import com.organizer.domain.common.mail.MailManager;
import com.organizer.domain.common.mail.MessageVariable;
import com.organizer.domain.model.user.*;
import com.organizer.domain.model.user.events.UserRegisteredEvent;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private RegistrationManagement registrationManagementMock;
    private DomainEventPublisher eventPublisherMock;
    private MailManager mailManagerMock;
    private  UserServiceImpl instance;

    @Before
    public void setUp(){
        registrationManagementMock = mock(RegistrationManagement.class);
        eventPublisherMock = mock(DomainEventPublisher.class);
        mailManagerMock = mock(MailManager.class);
        instance = new UserServiceImpl(registrationManagementMock, eventPublisherMock,
                mailManagerMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void register_nullCommand_shouldFail() throws RegistrationException{
        instance.register(null);
    }

    @Test(expected = RegistrationException.class)
    public void register_existingUsername_shouldFail() throws RegistrationException{
        String username = "existing";
        String emailAddress = "sunny@local.com";
        String password = "CorrectPassword123";
        doThrow(UsernameExistsException.class).when(registrationManagementMock)
                .register(username, emailAddress, password);

        RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);
        instance.register(command);
    }

    @Test(expected = RegistrationException.class)
    public void register_existingEmailAddress_shouldFail() throws RegistrationException{
        String username = "sunny";
        String emailAddress = "existing@local.com";
        String password = "CorrectPassword123";
        doThrow(EmailAddressExistsException.class).when(registrationManagementMock)
                .register(username, emailAddress, password);

        RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);
        instance.register(command);
    }


    @Test
    public void register_validCommand_shouldSucceed() throws RegistrationException {
        String username = "sunny";
        String emailAddress = "sunny@local.com";
        String password = "MyPassword!";
        User newUser = User.create(username, emailAddress, password);
        when(registrationManagementMock.register(username, emailAddress, password))
                .thenReturn(newUser);
        RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);

        instance.register(command);

        verify(mailManagerMock).send(
                emailAddress,
                "Witaj w Organizer",
                "Witaj.ftl",
                MessageVariable.from("user", newUser)
        );
        verify(eventPublisherMock).publish(new UserRegisteredEvent(newUser));
    }

}
