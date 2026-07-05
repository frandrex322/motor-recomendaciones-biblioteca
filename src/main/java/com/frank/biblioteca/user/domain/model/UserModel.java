package com.frank.biblioteca.user.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.frank.biblioteca.user.domain.model.value_object.UserRole;
import com.frank.biblioteca.user.domain.model.value_object.UserStatus;

public class UserModel {
    private final UUID id;
    private String name;
    private String email;
    private String password;
    private UserStatus status;
    private LocalDate createdAt;
    private UserRole role;

    public UserModel(String name, 
        String email, String password, UserRole role) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDate.now();
        System.out.println("UserModel created with ID: " + this.id + ", Name: " + this.name + ", Email: " + this.email + ", Status: " + this.status + ", Created At: " + this.createdAt);
    }

    public void setActive() {
        if(this.status == UserStatus.ACTIVE) {
            throw new IllegalStateException("Cannot set status to ACTIVE when the user is INACTIVE");
        }
        this.status = UserStatus.ACTIVE;
    }

    public void setInactive() {
        if(this.status == UserStatus.INACTIVE) {
            throw new IllegalStateException("Cannot set status to INACTIVE when the user is ACTIVE");
        }
        this.status = UserStatus.INACTIVE;
    }

    public UUID getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public UserStatus getStatus() { return status; }

    public LocalDate getCreatedAt() { return createdAt; }

    public UserRole getRole() { return role; }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
