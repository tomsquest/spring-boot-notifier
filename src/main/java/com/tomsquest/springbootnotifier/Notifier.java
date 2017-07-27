package com.tomsquest.springbootnotifier;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class Notifier implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            onReady((ApplicationReadyEvent) event);
        } else if (event instanceof ApplicationFailedEvent) {
            onFailure((ApplicationFailedEvent) event);
        }
    }

    private void onReady(ApplicationReadyEvent event) {
        System.out.println("Notifier.onReady " + event.getTimestamp());
    }

    private void onFailure(ApplicationFailedEvent event) {
        System.out.println("Notifier.onFailure " + event.getTimestamp());
    }
}
