package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.repository.RoomDAO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetService {
    @Autowired
    RoomDAO roomDAO;

    public void resetGame(HttpSession session, String partidaId) {
        session.setAttribute("currentRoomId", "1");
        session.setAttribute("coinsCollected", 0);
        session.setAttribute("keysCollected", "");

        resetLlavesPartida(partidaId);
        resetMonedasPartida(partidaId);
    }

    private void resetMonedasPartida(String partidaId) {
        roomDAO.resetMonedas(partidaId);
    }

    private void resetLlavesPartida(String partidaId) {
        roomDAO.resetLlaves(partidaId);
    }

}
