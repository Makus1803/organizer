package com.organizer.infrastructure.repository;

import com.organizer.domain.model.user.User;
import com.organizer.domain.model.user.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class HibernateUserRepositoryTests {

    @TestConfiguration
    public static class UserRepositoryTestContextConfiguration {
        @Bean
        public UserRepository userRepository(EntityManager entityManager) {
            return new HibernateUserRepository(entityManager);
        }
    }

    @Autowired
    private UserRepository repository;

    @Test(expected = PersistenceException.class)
    public void save_nullUsernameUser_shouldFail(){
        User invalidUser = User.create(
                null, "sunny@local.com", "CorrectPassword");
        repository.save(invalidUser);
    }

    @Test(expected = PersistenceException.class)
    public void save_nullEmailAddressUser_shouldFail(){
        User invalidUser = User.create(
                "sunny", null, "CorrectPassword");
        repository.save(invalidUser);
    }

    @Test(expected = PersistenceException.class)
    public void save_nullPasswordUser_shouldFail(){
        User invalidUser = User.create(
                "sunny", "sunny@local.com", null);
        repository.save(invalidUser);
    }

    @Test
    public void save_validUser_shouldSuccess(){
        String username = "sunny";
        String emailAddress = "sunny@local.com";
        User newUser = User.create(username, emailAddress, "CorrectPassword");
        repository.save(newUser);
        assertNotNull("Id nowego użytkownika powinno zostać wygenerowane", newUser.getId());
        assertNotNull("Data utworzenia użytkownika powinna zostać wygenerowana", newUser.getCreatedDate());
        assertEquals(username, newUser.getUsername());
        assertEquals(emailAddress, newUser.getEmailAddress());
        assertEquals("", newUser.getFirstName());
        assertEquals("", newUser.getLastName());
    }

    @Test
    public void save_usernameAlreadyExist_shouldFail(){
        String username = "sunny";
        String emailAddress = "sunny@local.com";
        User alreadyExist = User.create(username, emailAddress, "CorrectPassword");
        repository.save(alreadyExist);

        try{
            User newUser = User.create(username, "new@local.com", "CorrectPassword");
            repository.save(newUser);
        } catch (Exception e){
            assertEquals(ConstraintViolationException.class.toString(), e.getCause().getClass().toString());
        }
    }

    @Test
    public void save_emailAddressAlreadyExist_shouldFail(){
        String username = "sunny";
        String emailAddress = "sunny@local.com";
        User alreadyExist = User.create(username, emailAddress, "CorrectPassword");
        repository.save(alreadyExist);

        try{
            User newUser = User.create("newUser", emailAddress, "CorrectPassword");
            repository.save(newUser);
        } catch (Exception e){
            assertEquals(ConstraintViolationException.class.toString(), e.getCause().getClass().toString());
        }
    }

    @Test
    public void findByEmailAddress_notExist_shouldReturnEmptyResult(){
        String emailAddress = "sunny@local.com";
        User user = repository.findByEmailAddress(emailAddress);
        assertNull("Uzytkownik nie powinien zostac znaleziony", user);
    }

    @Test
    public void findByEmailAddress_exist_shouldReturnResult(){
        String username = "sunny";
        String emailAddress = "sunny@local.com";
        User newUser = User.create(username, emailAddress, "CorrectPassword");
        repository.save(newUser);
        User found = repository.findByEmailAddress(emailAddress);
        assertEquals("Username powinno byc takie same", username, found.getUsername());
    }

    @Test
    public void findByUsername_notExist_shouldReturnEmptyResult(){
        String username = "sunny";
        User user = repository.findByEmailAddress(username);
        assertNull("Uzytkownik nie powinien zostac znaleziony", user);
    }

    @Test
    public void findByUsername_exist_shouldReturnResult(){
        String username = "sunny";
        String emailAddress = "sunny@local.com";
        User newUser = User.create(username, emailAddress, "CorrectPassword");
        repository.save(newUser);
        User found = repository.findByUsername(username);
        assertEquals("Emaile powinny byc takie same", emailAddress, found.getEmailAddress());
    }

}
