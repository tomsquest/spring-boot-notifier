package com.tomsquest.springbootnotifier;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

        ProcessBuilder pb = new ProcessBuilder(
                "notify-send",
                "Spring Application Ready",
                "Ready "+ new Date(event.getApplicationContext().getStartupDate()),
                "-i", successIconPath());
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroyForcibly();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String successIconPath() {
        return iconPath("spring-boot-logo.green.png");
    }

    private String failureIconPath() {
        return iconPath("spring-boot-logo.red.png");
    }

    private String iconPath(String iconName) {
        try {
            return new ClassPathResource(iconName).getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void onFailure(ApplicationFailedEvent event) {
        System.out.println("Notifier.onFailure " + event.getTimestamp());
    }
}
