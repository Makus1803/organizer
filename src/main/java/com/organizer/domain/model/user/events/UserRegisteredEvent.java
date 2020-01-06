package com.organizer.domain.model.user.events;


import com.organizer.domain.common.event.DomainEvent;
import com.organizer.domain.model.user.User;
import org.springframework.util.Assert;

import java.util.Objects;

public class UserRegisteredEvent extends DomainEvent {

    private User user;

    public UserRegisteredEvent(User user) {
        super(user);
        Assert.notNull(user, "Parameter `user` must not be null");
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRegisteredEvent)) return false;
        UserRegisteredEvent that = (UserRegisteredEvent) o;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "UserRegisteredEvent{" +
                "user='" + user + '\'' +
                "timestamp='" + getTimestamp() + '\'' +
                '}';
    }
}
