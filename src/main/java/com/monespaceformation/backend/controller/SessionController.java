package com.monespaceformation.backend.controller;

import com.monespaceformation.backend.model.SessionFormation;
import com.monespaceformation.backend.repository.InscriptionRepository;
import com.monespaceformation.backend.repository.SessionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(originPatterns = {"http://localhost:5173", "https://*.vercel.app"})
public class SessionController {

    private final SessionRepository sessionRepository;
    private final InscriptionRepository inscriptionRepository;

    public SessionController(SessionRepository sessionRepository, InscriptionRepository inscriptionRepository) {
        this.sessionRepository = sessionRepository;
        this.inscriptionRepository = inscriptionRepository;
    }

    // 1) Liste complète (Catalogue)
    @GetMapping
    public List<SessionFormation> getAllSessions() {
        return sessionRepository.findAll();
    }

    // 2) Détails d'une session
    @GetMapping("/{id}")
    public ResponseEntity<SessionFormation> getSessionById(@PathVariable String id) {
        Optional<SessionFormation> session = sessionRepository.findById(id);
        return session.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3) Création
    @PostMapping
    public ResponseEntity<SessionFormation> createSession(@RequestBody SessionFormation session) {
        try {
            // placesReservees : si null -> 0, si < 0 -> 0
            Integer pr = session.getPlacesReservees();
            if (pr == null || pr < 0) {
                session.setPlacesReservees(0);
            }

            SessionFormation saved = sessionRepository.save(session);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 4) Update (partiel)
    @PutMapping("/{id}")
    public ResponseEntity<SessionFormation> updateSession(
            @PathVariable String id,
            @RequestBody SessionFormation sessionUpdate
    ) {
        try {
            Optional<SessionFormation> sessionOpt = sessionRepository.findById(id);
            if (sessionOpt.isEmpty()) return ResponseEntity.notFound().build();

            SessionFormation session = sessionOpt.get();

            if (sessionUpdate.getTitle() != null) session.setTitle(sessionUpdate.getTitle());
            if (sessionUpdate.getDates() != null) session.setDates(sessionUpdate.getDates());
            if (sessionUpdate.getLieu() != null) session.setLieu(sessionUpdate.getLieu());
            if (sessionUpdate.getPrice() != null) session.setPrice(sessionUpdate.getPrice());
            if (sessionUpdate.getLevel() != null) session.setLevel(sessionUpdate.getLevel());
            if (sessionUpdate.getCategory() != null) session.setCategory(sessionUpdate.getCategory());
            if (sessionUpdate.getDesc() != null) session.setDesc(sessionUpdate.getDesc());

            // attention: si placesTotales est Integer, on check null aussi
            Integer pt = sessionUpdate.getPlacesTotales();
            if (pt != null && pt > 0) session.setPlacesTotales(pt);

            Integer pr = sessionUpdate.getPlacesReservees();
            if (pr != null && pr >= 0) session.setPlacesReservees(pr);

            SessionFormation saved = sessionRepository.save(session);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // 5) Suppression (bloquée si inscriptions liées)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable String id) {
        try {
            Optional<SessionFormation> sessionOpt = sessionRepository.findById(id);
            if (sessionOpt.isEmpty()) return ResponseEntity.notFound().build();

            List<com.monespaceformation.backend.model.Inscription> inscriptions =
                    inscriptionRepository.findBySessionId(id);

            if (!inscriptions.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        "Impossible de supprimer cette session car " + inscriptions.size()
                                + " utilisateur(s) y sont déjà inscrit(s). Veuillez d'abord supprimer les inscriptions associées."
                );
            }

            sessionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
