package com.monespaceformation.backend.controller;

import com.monespaceformation.backend.model.SessionFormation;
import com.monespaceformation.backend.repository.SessionRepository;
import com.monespaceformation.backend.repository.InscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
// 👇 Autorise React (Vite utilise souvent le port 5173, sinon mettez 3000)
@CrossOrigin(originPatterns = {"http://localhost:5173", "https://*.vercel.app"})
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private InscriptionRepository inscriptionRepository;

    // 1. Ça, c'est ce qui fait marcher votre Catalogue (La liste complète)
    @GetMapping
    public List<SessionFormation> getAllSessions() {
        return sessionRepository.findAll();
    }

    // 👇 2. AJOUTEZ CECI : C'est ce qui manque pour la page Détails !
    // Ça permet de trouver une formation précise grâce à son ID
    @GetMapping("/{id}")
    public ResponseEntity<SessionFormation> getSessionById(@PathVariable String id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        // On cherche dans la base de données
        Optional<SessionFormation> session = sessionRepository.findById(id);

        // Si on trouve, on renvoie la formation. Sinon, erreur 404.
        if (session.isPresent()) {
            return ResponseEntity.ok(session.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 3. CRÉER UNE NOUVELLE SESSION (POST)
    @PostMapping
    public ResponseEntity<SessionFormation> createSession(@RequestBody SessionFormation session) {
        try {
            // Initialiser les places réservées à 0 si non défini
            if (session.getPlacesReservees() < 0) {
                session.setPlacesReservees(0);
            }
            
            SessionFormation saved = sessionRepository.save(session);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Mettre à jour une session
     * PUT /api/sessions/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<SessionFormation> updateSession(@PathVariable String id, @RequestBody SessionFormation sessionUpdate) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().build();
            }
            Optional<SessionFormation> sessionOpt = sessionRepository.findById(id);
            if (sessionOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            SessionFormation session = sessionOpt.get();

            // Mettre à jour les champs
            if (sessionUpdate.getTitle() != null) {
                session.setTitle(sessionUpdate.getTitle());
            }
            if (sessionUpdate.getDates() != null) {
                session.setDates(sessionUpdate.getDates());
            }
            if (sessionUpdate.getLieu() != null) {
                session.setLieu(sessionUpdate.getLieu());
            }
            if (sessionUpdate.getPlacesTotales() > 0) {
                session.setPlacesTotales(sessionUpdate.getPlacesTotales());
            }
            if (sessionUpdate.getPlacesReservees() >= 0) {
                session.setPlacesReservees(sessionUpdate.getPlacesReservees());
            }
            if (sessionUpdate.getPrice() != null) {
                session.setPrice(sessionUpdate.getPrice());
            }
            if (sessionUpdate.getLevel() != null) {
                session.setLevel(sessionUpdate.getLevel());
            }
            if (sessionUpdate.getCategory() != null) {
                session.setCategory(sessionUpdate.getCategory());
            }
            if (sessionUpdate.getDesc() != null) {
                session.setDesc(sessionUpdate.getDesc());
            }

            SessionFormation saved = sessionRepository.save(session);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Supprimer une session
     * DELETE /api/sessions/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable String id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().build();
            }
            Optional<SessionFormation> sessionOpt = sessionRepository.findById(id);
            if (sessionOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Vérifier s'il y a des inscriptions liées à cette session
            List<com.monespaceformation.backend.model.Inscription> inscriptions = inscriptionRepository.findBySessionId(id);

            if (!inscriptions.isEmpty()) {
                return ResponseEntity.badRequest().body("Impossible de supprimer cette session car " + inscriptions.size() + " utilisateur(s) y sont déjà inscrit(s). Veuillez d'abord supprimer les inscriptions associées.");
            }

            sessionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}