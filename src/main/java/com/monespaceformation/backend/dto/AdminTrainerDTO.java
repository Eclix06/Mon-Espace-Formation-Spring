package com.monespaceformation.backend.dto;

/**
 * DTO pour les formateurs dans l'interface d'administration
 */
public class AdminTrainerDTO {
    private String id;
    private String fullname;
    private String email;
    private String speciality;
    private int activeSessions;

    public AdminTrainerDTO() {}

    public AdminTrainerDTO(String id, String fullname, String email, String speciality, int activeSessions) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.speciality = speciality;
        this.activeSessions = activeSessions;
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSpeciality() { return speciality; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }

    public int getActiveSessions() { return activeSessions; }
    public void setActiveSessions(int activeSessions) { this.activeSessions = activeSessions; }
}

