package com.esliceu.SegonaPracticaObligatoria.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class ResetService {

    public void resetGame(HttpSession session) {
        session.setAttribute("currentRoomId", "1");
        session.setAttribute("coinsCollected", 0);
        session.setAttribute("keysCollected", "");
    }
}
