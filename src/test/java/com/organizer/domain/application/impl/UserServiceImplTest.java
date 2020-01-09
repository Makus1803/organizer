package com.organizer.domain.application.impl;

import com.organizer.domain.application.commands.RegistrationCommand;
import com.organizer.domain.common.event.DomainEventPublisher;
import com.organizer.domain.common.mail.MailManager;
import com.organizer.domain.common.mail.MessageVariable;
import com.organizer.domain.model.user.*;
import com.organizer.domain.model.user.events.UserRegisteredEvent;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private RegistrationManagement registrationManagementMock;
    private DomainEventPublisher eventPublisherMock;
    private MailManager mailManagerMock;
    private UserRepository userRepositoryMock;
    private UserServiceImpl instance;

    @Before
    public void setUp(){
        registrationManagementMock = mock(RegistrationManagement.class);
        eventPublisherMock = mock(DomainEventPublisher.class);
        mailManagerMock = mock(MailManager.class);
        userRepositoryMock = mock(UserRepository.class);
        instance = new UserServiceImpl(registrationManagementMock, eventPublisherMock,
                mailManagerMock, userRepositoryMock);
    }

    //------------------- Method loadUserByUSERNAME()

    @Test
    public void loadUserByUsername_emptyUsername_shouldFail() {
        Exception exception = null;
        try{
            instance.loadUserByUsername("");
        } catch (Exception e){
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception instanceof UsernameNotFoundException);
        verify(userRepositoryMock, never()).findByUsername("");
        verify(userRepositoryMock, never()).findByEmailAddress("");
    }

    @Test
    public void loadUserByUsername_notExistUsername_shouldFail() {
        String notExistingUsername = "NotExist";
        when(userRepositoryMock.findByUsername(notExistingUsername)).thenReturn(null);
        Exception exception = null;
        try {
            instance.loadUserByUsername(notExistingUsername);
        } catch (Exception e){
            exception = e;
        }
        assertNotNull(exception);
        assertTrue(exception instanceof UsernameNotFoundException);
        verify(userRepositoryMock).findByUsername(notExistingUsername);
        verify(userRepositoryMock, never()).findByEmailAddress(notExistingUsername);
    }

    @Test
    public void loadUserByUsername_existUsername_shouldSucceed() throws IllegalAccessException {
        String existingUsername = "Exist";
        User foundUser = User.create(existingUsername, "user@local.com", "CorrectPassword");
        foundUser.updateName("Test", "User");
        FieldUtils.writeField(foundUser, "id", 1L, true);
        when(userRepositoryMock.findByUsername(existingUsername)).thenReturn(foundUser);
        Exception exception = null;
        UserDetails userDetails = null;
        try {
            userDetails = instance.loadUserByUsername(existingUsername);
        } catch (Exception e){
            exception = e;
        }
        assertNull(exception);
        verify(userRepositoryMock).findByUsername(existingUsername);
        verify(userRepositoryMock, never()).findByEmailAddress(existingUsername);
        assertNotNull(userDetails);
        assertEquals(existingUsername, userDetails.getUsername());
        assertTrue(userDetails instanceof SimpleUser);
    }

    //------------------- Method register()

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
