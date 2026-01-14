package com.monespaceformation.backend.config;

import com.monespaceformation.backend.model.Settings;
import com.monespaceformation.backend.model.Training;
import com.monespaceformation.backend.model.TrainingDocument;
import com.monespaceformation.backend.model.User;
import com.monespaceformation.backend.repository.SettingsRepository;
import com.monespaceformation.backend.repository.TrainingRepository;
import com.monespaceformation.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(TrainingRepository trainingRepository, UserRepository userRepository, 
                               PasswordEncoder passwordEncoder, SettingsRepository settingsRepository) {
        return args -> {
            // On ajoute la formation SEULEMENT si la base est vide
            if (trainingRepository.count() == 0) {
                Training t = new Training();
                t.setTitle("Développement Front-End avec React et TypeScript");
                t.setReference("FORM-DEV-001");
                t.setSessionRef("INS-2024-001234");
                t.setStartDate("15 Janvier 2025");
                t.setEndDate("19 Janvier 2025");
                t.setDuration("5 Jours");
                t.setLocation("42 Avenue des Champs-Élysées, 75008 Paris");

                t.setTrainerName("Jean-Pierre Martin");
                t.setTrainerRole("Expert React & TypeScript");
                t.setTrainerEmail("jp.martin@txlforma.fr");
                t.setStatus("A Venir");

                // Les documents de ta maquette
                t.setDocuments(Arrays.asList(
                        new TrainingDocument("Facture acquittée", "FAC-2024-001234", "Facture", "#"),
                        new TrainingDocument("Confirmation d'inscription", "INS-2024", "Confirmation", "#"),
                        new TrainingDocument("Convention de formation", "CONV-2024", "Convention", "#")
                ));

                trainingRepository.save(t);
                System.out.println("--- DONNÉES DE DÉMO AJOUTÉES (Formation React) ---");
            }

            // Création de l'utilisateur admin par défaut
            Optional<User> adminOpt = userRepository.findByEmail("admin@mef.fr");
            if (adminOpt.isEmpty()) {
                User admin = new User();
                admin.setNom("Administrateur");
                admin.setPrenom("Admin");
                admin.setEmail("admin@mef.fr");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                
                userRepository.save(admin);
                System.out.println("--- COMPTE ADMIN CRÉÉ (admin@mef.fr / admin123) ---");
            } else {
                System.out.println("--- COMPTE ADMIN EXISTE DÉJÀ ---");
            }

            // Création des paramètres par défaut de la plateforme
            if (!settingsRepository.existsById("default")) {
                Settings defaultSettings = new Settings();
                defaultSettings.setId("default");
                defaultSettings.setPlatformName("Mon Espace Formation");
                defaultSettings.setContactEmail("contact@mef.fr");
                defaultSettings.setFullPaymentRequired(true);
                defaultSettings.setCancellationDelayDays(7);
                defaultSettings.setRefundRatePercentage(80);
                defaultSettings.setMaintenanceMode(false);
                
                settingsRepository.save(defaultSettings);
                System.out.println("--- PARAMÈTRES PAR DÉFAUT CRÉÉS ---");
            }
        };
    }
}