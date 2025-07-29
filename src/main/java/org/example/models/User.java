package org.example.models;

import java.util.UUID;

public class User {
    private final UUID id;
    private String firstName;

    public User(String firstName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}