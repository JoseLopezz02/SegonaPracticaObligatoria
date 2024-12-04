package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Mapa;
import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StartController {

    @Autowired
    GameCanvasService gameCanvasService;

    @GetMapping("/start")
    public String getStart(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");

        List<Mapa> mapas = gameCanvasService.getAllMaps();
        model.addAttribute("message", username);
        model.addAttribute("mapas", mapas);

        return "start";
    }

    @PostMapping("/start")
    public String postStart(@RequestParam(required = false) String mapName, HttpSession session, Model model) throws JsonProcessingException {
        if (mapName == null || mapName.isEmpty()) {
            model.addAttribute("error", "Por favor, selecciona un mapa antes de empezar.");

            // Vuelve a cargar los mapas en el modelo
            List<Mapa> mapas = gameCanvasService.getAllMaps();
            model.addAttribute("mapas", mapas);

            return "start";
        }

        String mapId = gameCanvasService.getMapIdByName(mapName);
        String userId = String.valueOf(session.getAttribute("userId"));
        String partidaId = gameCanvasService.createNewPartida(userId);
        String idRoomInicial = gameCanvasService.getInitialRoomIdByMapId(mapId);

        Room room = gameCanvasService.getRoom(mapId, idRoomInicial);
        Partida partida = gameCanvasService.getPartidaById(partidaId);
        String roomData = gameCanvasService.convertDataToString(room, partida);
        gameCanvasService.updateCurrentRoomPartida(idRoomInicial, partidaId);

        model.addAttribute("roomData", roomData);

        session.setAttribute("mapName", mapName);
        session.setAttribute("partidaId", partidaId);
        session.setAttribute("mapId", mapId);
        session.setAttribute("currentRoomId", idRoomInicial);

        model.addAttribute("coinsCollected", partida.getCoinsCollected());

        return "gameCanvas";
    }
}
