package com.monespaceformation.backend.controller;

import com.monespaceformation.backend.dto.SessionDto;
import com.monespaceformation.backend.model.SessionFormation;
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

    public SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public ResponseEntity<List<SessionDto>> getAllSessions() {
        List<SessionFormation> sessions = sessionRepository.findAll();

        List<SessionDto> dto = sessions.stream()
                .map(this::toDto)
                .toList();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDto> getSessionById(@PathVariable String id) {
        Optional<SessionFormation> session = sessionRepository.findById(id);
        return session
                .map(value -> ResponseEntity.ok(toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private SessionDto toDto(SessionFormation s) {
        return new SessionDto(
                s.getId(),
                s.getTitle(),
                s.getReference(),
                s.getDesc(),
                s.getStartDate(),
                s.getEndDate(),
                s.getDuration(),
                s.getTime(),
                s.getPrice(),
                s.getCategory(),
                s.getLevel(),
                s.getLocation(),
                s.getTrainerName(),
                s.getPlacesTotales(),
                s.getSessions()
        );
    }
}
