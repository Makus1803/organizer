package com.organizer.infrastructure.repository;

import com.organizer.domain.model.user.User;
import com.organizer.domain.model.user.UserRepository;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class HibernateUserRepository extends HibernateSupport implements UserRepository {

    HibernateUserRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public User findByUsername(String username) {
        Query<User> query = getSessions().createQuery(
                "from User where username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    @Override
    public User findByEmailAddress(String emailAddress) {
        Query<User> query = getSessions().createQuery(
                "from User where emailAddress = :emailAddress", User.class);
        query.setParameter("emailAddress", emailAddress);
        return query.uniqueResult();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }
}
