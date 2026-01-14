package com.monespaceformation.backend.dto;

import com.monespaceformation.backend.model.SessionFormation;

/**
 * DTO pour représenter une session avec la note de l'élève
 */
public class SessionWithNote {
    private SessionFormation session;
    private Double note; // Note attribuée par le formateur (sur 20)

    public SessionWithNote() {}

    public SessionWithNote(SessionFormation session, Double note) {
        this.session = session;
        this.note = note;
    }

    public SessionFormation getSession() {
        return session;
    }

    public void setSession(SessionFormation session) {
        this.session = session;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }
}

