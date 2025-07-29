package org.example.models;

import java.util.UUID;

public class Task {
    private final UUID id;
    private String title;
    private String description;
    private boolean done;
    private User user;

    public Task(String title, String description, User user) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.done = false;
        this.user = user;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isDone() { return done; }
    public User getUser() { return user; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDone(boolean done) { this.done = done; }
}