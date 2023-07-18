package com.example.letstalkapplication;

public class Message {
    String senderId, message;

    public Message(String senderId, String message) {
        this.senderId = senderId;
        this.message = message;
    }

    public Message() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
