package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import com.esliceu.SegonaPracticaObligatoria.services.ResetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResetController {
    @Autowired
    GameCanvasService gameCanvasService;
    @Autowired
    ResetService resetService;

    @GetMapping("/reset")
    public String getReset(HttpSession session, Model model) throws JsonProcessingException {

        String partidaId = (String) session.getAttribute("partidaId");
        String mapId = (String) session.getAttribute("mapId");

        resetService.resetGame(session, partidaId, mapId);

        String currentRoomId = (String) session.getAttribute("currentRoomId");
        Room room = gameCanvasService.getRoom(mapId, currentRoomId);
        Partida partida = gameCanvasService.getPartidaById(partidaId);

        String roomData = gameCanvasService.convertDataToString(room, partida);

        model.addAttribute("roomData", roomData);
        model.addAttribute("coinsCollected", 0);
        model.addAttribute("keysCollected", "Ninguna");
        model.addAttribute("message", "¡Juego reiniciado! Comienzas desde la habitación inicial.");

        return "gameCanvas";
    }
}
