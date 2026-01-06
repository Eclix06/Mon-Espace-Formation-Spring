package com.monespaceformation.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Modèle pour les paramètres de la plateforme
 * Stocke la configuration globale de l'application
 */
@Document(collection = "settings")
public class Settings {
    
    @Id
    private String id;
    
    // Configuration Stripe
    private String stripePublicKey;
    private String stripeSecretKey;
    
    // Paiement
    private Boolean fullPaymentRequired; // Paiement intégral obligatoire
    
    // Politique d'annulation et remboursement
    private Integer cancellationDelayDays; // Délai d'annulation en jours
    private Integer refundRatePercentage; // Taux de remboursement en pourcentage
    
    // Autres paramètres généraux
    private String platformName;
    private String contactEmail;
    private Boolean maintenanceMode;

    public Settings() {
        // Valeurs par défaut
        this.fullPaymentRequired = true;
        this.cancellationDelayDays = 7;
        this.refundRatePercentage = 80;
        this.maintenanceMode = false;
    }

    // Getters et Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStripePublicKey() { return stripePublicKey; }
    public void setStripePublicKey(String stripePublicKey) { this.stripePublicKey = stripePublicKey; }

    public String getStripeSecretKey() { return stripeSecretKey; }
    public void setStripeSecretKey(String stripeSecretKey) { this.stripeSecretKey = stripeSecretKey; }

    public Boolean getFullPaymentRequired() { return fullPaymentRequired; }
    public void setFullPaymentRequired(Boolean fullPaymentRequired) { this.fullPaymentRequired = fullPaymentRequired; }

    public Integer getCancellationDelayDays() { return cancellationDelayDays; }
    public void setCancellationDelayDays(Integer cancellationDelayDays) { this.cancellationDelayDays = cancellationDelayDays; }

    public Integer getRefundRatePercentage() { return refundRatePercentage; }
    public void setRefundRatePercentage(Integer refundRatePercentage) { this.refundRatePercentage = refundRatePercentage; }

    public String getPlatformName() { return platformName; }
    public void setPlatformName(String platformName) { this.platformName = platformName; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public Boolean getMaintenanceMode() { return maintenanceMode; }
    public void setMaintenanceMode(Boolean maintenanceMode) { this.maintenanceMode = maintenanceMode; }
}

