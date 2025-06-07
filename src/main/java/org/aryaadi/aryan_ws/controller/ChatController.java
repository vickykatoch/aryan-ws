package org.aryaadi.aryan_ws.controller;

import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final SimpMessagingTemplate template;

    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    // Broadcast message to all
    @MessageMapping("/broadcast")
    public void broadcastMessage(@Payload String message) {
        template.convertAndSend("/topic/messages", message);
    }

    // Send private message to specific user
    @MessageMapping("/private-message")
    public void privateMessage(@Payload PrivateMessage msg) {
        template.convertAndSendToUser(msg.getRecipient(), "/queue/messages", msg.getContent());
    }

    public static class PrivateMessage {
        private String recipient;
        private String content;

        public PrivateMessage() {
        }

        public String getRecipient() {
            return recipient;
        }

        public void setRecipient(String recipient) {
            this.recipient = recipient;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }
}
