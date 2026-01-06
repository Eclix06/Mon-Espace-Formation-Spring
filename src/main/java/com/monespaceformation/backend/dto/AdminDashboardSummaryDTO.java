package com.monespaceformation.backend.dto;

/**
 * DTO pour les statistiques globales du dashboard d'administration
 */
public class AdminDashboardSummaryDTO {
    private int totalInscriptions;
    private int totalTrainings;
    private int totalSessions;
    private int totalUsers;

    public AdminDashboardSummaryDTO() {}

    public AdminDashboardSummaryDTO(int totalInscriptions, int totalTrainings, int totalSessions, int totalUsers) {
        this.totalInscriptions = totalInscriptions;
        this.totalTrainings = totalTrainings;
        this.totalSessions = totalSessions;
        this.totalUsers = totalUsers;
    }

    // Getters et Setters
    public int getTotalInscriptions() { return totalInscriptions; }
    public void setTotalInscriptions(int totalInscriptions) { this.totalInscriptions = totalInscriptions; }

    public int getTotalTrainings() { return totalTrainings; }
    public void setTotalTrainings(int totalTrainings) { this.totalTrainings = totalTrainings; }

    public int getTotalSessions() { return totalSessions; }
    public void setTotalSessions(int totalSessions) { this.totalSessions = totalSessions; }

    public int getTotalUsers() { return totalUsers; }
    public void setTotalUsers(int totalUsers) { this.totalUsers = totalUsers; }
}

