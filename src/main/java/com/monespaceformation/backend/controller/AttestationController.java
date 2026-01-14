package com.monespaceformation.backend.controller;

import com.monespaceformation.backend.dto.AdminInscriptionDTO;
import com.monespaceformation.backend.model.Inscription;
import com.monespaceformation.backend.model.SessionFormation;
import com.monespaceformation.backend.model.User;
import com.monespaceformation.backend.repository.InscriptionRepository;
import com.monespaceformation.backend.repository.SessionRepository;
import com.monespaceformation.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller pour gérer les attestations
 * Récupère les inscriptions terminées/validées pour générer des attestations
 */
@RestController
@RequestMapping("/api/attestations")
@CrossOrigin(originPatterns = {"http://localhost:5173", "https://*.vercel.app"})
public class AttestationController {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Récupérer toutes les inscriptions éligibles pour attestation (terminées/validées)
     * GET /api/attestations
     */
    @GetMapping
    public ResponseEntity<List<AdminInscriptionDTO>> getEligibleAttestations() {
        try {
            List<Inscription> allInscriptions = inscriptionRepository.findAll();
            List<AdminInscriptionDTO> result = new ArrayList<>();

            for (Inscription insc : allInscriptions) {
                // Filtrer les inscriptions avec statut "TERMINÉE", "VALIDÉE", "CONFIRMED" ou "COMPLETED"
                String status = insc.getStatus();
                if (status != null && (
                    status.equalsIgnoreCase("TERMINÉE") || 
                    status.equalsIgnoreCase("TERMINE") ||
                    status.equalsIgnoreCase("VALIDÉE") ||
                    status.equalsIgnoreCase("VALIDE") ||
                    status.equalsIgnoreCase("CONFIRMED") ||
                    status.equalsIgnoreCase("COMPLETED")
                )) {
                    // Récupérer les informations de l'utilisateur
                    String userName = "N/A";
                    String userEmail = "N/A";
                    String userId = insc.getUserId();
                    Optional<User> userOpt = (userId != null) ? userRepository.findById(userId) : Optional.empty();
                    if (userOpt.isPresent()) {
                        User user = userOpt.get();
                        userName = (user.getPrenom() != null ? user.getPrenom() : "") + " " + 
                                  (user.getNom() != null ? user.getNom() : "");
                        userName = userName.trim();
                        if (userName.isEmpty() && insc.getParticipant() != null) {
                            userName = (insc.getParticipant().getPrenom() != null ? insc.getParticipant().getPrenom() : "") + " " +
                                      (insc.getParticipant().getNom() != null ? insc.getParticipant().getNom() : "");
                            userName = userName.trim();
                            userEmail = insc.getParticipant().getEmail() != null ? insc.getParticipant().getEmail() : "N/A";
                        } else {
                            userEmail = user.getEmail() != null ? user.getEmail() : "N/A";
                        }
                    } else if (insc.getParticipant() != null) {
                        userName = (insc.getParticipant().getPrenom() != null ? insc.getParticipant().getPrenom() : "") + " " +
                                  (insc.getParticipant().getNom() != null ? insc.getParticipant().getNom() : "");
                        userName = userName.trim();
                        userEmail = insc.getParticipant().getEmail() != null ? insc.getParticipant().getEmail() : "N/A";
                    }

                    // Récupérer les informations de la session/formation
                    String trainingTitle = "N/A";
                    String sessionDate = "N/A";
                    String sessionId = insc.getSessionId();
                    Optional<SessionFormation> sessionOpt = (sessionId != null) ? sessionRepository.findById(sessionId) : Optional.empty();
                    if (sessionOpt.isPresent()) {
                        SessionFormation session = sessionOpt.get();
                        trainingTitle = session.getTitle() != null ? session.getTitle() : "N/A";
                        sessionDate = session.getDates() != null ? session.getDates() : "N/A";
                    }

                    AdminInscriptionDTO dto = new AdminInscriptionDTO(
                        insc.getId(),
                        userName,
                        userEmail,
                        trainingTitle,
                        sessionDate,
                        insc.getDateInscription() != null ? insc.getDateInscription() : null,
                        insc.getStatus() != null ? insc.getStatus() : "N/A"
                    );

                    result.add(dto);
                }
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}

