package com.organizer.domain.common.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class DefaultDomainEventPublisher implements DomainEventPublisher {
    @Autowired
    private ApplicationEventPublisher actualPublisher;

    @Override
    public void publish(DomainEvent event) {
        actualPublisher.publishEvent(event);
    }
}
