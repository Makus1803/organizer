package com.organizer.domain.common.event;

import org.springframework.context.ApplicationEvent;

public class DomainEvent extends ApplicationEvent {
    public DomainEvent(Object source) {
        super(source);
    }

    public long occurredAt(){
        return getTimestamp();
    }
}
