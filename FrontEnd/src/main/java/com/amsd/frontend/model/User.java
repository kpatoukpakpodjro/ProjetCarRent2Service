package com.amsd.frontend.model;

public class User {
    private Long id;
    private String username;
    private String email;
    private String role;
    private static long userCounter = 0;
    // Constructeur
    public User() {
        this.id = ++userCounter;
    }

    public User(String username, String email, String role) {
        this.id = ++userCounter;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Getters et setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }

    public static void setUserCounter(long value) {
        userCounter = value;
    }
}
