package com.monespaceformation.backend.dto;

import java.time.LocalDate;

/**
 * DTO pour les inscriptions dans l'interface d'administration
 * Contient les informations enrichies (nom utilisateur, titre formation, etc.)
 */
public class AdminInscriptionDTO {
    private String id;
    private String userName;
    private String userEmail;
    private String trainingTitle;
    private String sessionDate;
    private LocalDate registrationDate;
    private String status;

    public AdminInscriptionDTO() {}

    public AdminInscriptionDTO(String id, String userName, String userEmail, String trainingTitle, 
                               String sessionDate, LocalDate registrationDate, String status) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.trainingTitle = trainingTitle;
        this.sessionDate = sessionDate;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getTrainingTitle() { return trainingTitle; }
    public void setTrainingTitle(String trainingTitle) { this.trainingTitle = trainingTitle; }

    public String getSessionDate() { return sessionDate; }
    public void setSessionDate(String sessionDate) { this.sessionDate = sessionDate; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

