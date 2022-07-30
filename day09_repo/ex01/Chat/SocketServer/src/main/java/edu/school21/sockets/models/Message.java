package edu.school21.sockets.models;

import java.time.LocalDateTime;

public class Message {
    private String content;
    private LocalDateTime time;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public Message(String content, LocalDateTime time) {
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
