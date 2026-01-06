package com.monespaceformation.backend.dto;

/**
 * DTO pour les sessions dans l'interface d'administration
 */
public class AdminSessionDTO {
    private String id;
    private String trainingId;
    private String trainerName;
    private String startDate;
    private String endDate;
    private int capacity;
    private String location;
    private String status;

    public AdminSessionDTO() {}

    public AdminSessionDTO(String id, String trainingId, String trainerName, String startDate, 
                          String endDate, int capacity, String location, String status) {
        this.id = id;
        this.trainingId = trainingId;
        this.trainerName = trainerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.location = location;
        this.status = status;
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTrainingId() { return trainingId; }
    public void setTrainingId(String trainingId) { this.trainingId = trainingId; }

    public String getTrainerName() { return trainerName; }
    public void setTrainerName(String trainerName) { this.trainerName = trainerName; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

