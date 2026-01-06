package com.monespaceformation.backend.controller;

import com.monespaceformation.backend.model.Settings;
import com.monespaceformation.backend.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller pour gérer les paramètres de la plateforme
 */
@RestController
@RequestMapping("/api/settings")
@CrossOrigin(originPatterns = {"http://localhost:5173", "https://*.vercel.app"})
public class SettingsController {

    @Autowired
    private SettingsRepository settingsRepository;

    private static final String DEFAULT_SETTINGS_ID = "default";

    /**
     * Récupérer les paramètres de la plateforme
     * GET /api/settings
     */
    @GetMapping
    public ResponseEntity<Settings> getSettings() {
        Optional<Settings> settingsOpt = settingsRepository.findById(DEFAULT_SETTINGS_ID);
        
        if (settingsOpt.isPresent()) {
            return ResponseEntity.ok(settingsOpt.get());
        } else {
            // Créer des paramètres par défaut s'ils n'existent pas
            Settings defaultSettings = new Settings();
            defaultSettings.setId(DEFAULT_SETTINGS_ID);
            Settings saved = settingsRepository.save(defaultSettings);
            return ResponseEntity.ok(saved);
        }
    }

    /**
     * Sauvegarder les paramètres de la plateforme
     * PUT /api/settings
     */
    @PutMapping
    public ResponseEntity<Settings> updateSettings(@RequestBody Settings settings) {
        // S'assurer que l'ID est toujours "default"
        settings.setId(DEFAULT_SETTINGS_ID);
        
        // Si le paiement intégral est obligatoire, on ne peut pas le désactiver
        if (settings.getFullPaymentRequired() == null) {
            settings.setFullPaymentRequired(true);
        }
        
        Settings saved = settingsRepository.save(settings);
        return ResponseEntity.ok(saved);
    }
}

