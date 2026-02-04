package com.example.todo.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReminderPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public ReminderPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendReminder(String message) {
        messagingTemplate.convertAndSend("/topic/reminders", message);
    }

}

