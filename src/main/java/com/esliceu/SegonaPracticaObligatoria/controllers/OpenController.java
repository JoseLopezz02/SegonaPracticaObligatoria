package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OpenController {
    @Autowired
    GameCanvasService gameCanvasService;
    @GetMapping("/open")
    public String getOpen(HttpSession session, @RequestParam String direction, Model model){
        String mapId = (String) session.getAttribute("mapId");
        String partidaId = (String) session.getAttribute("partidaId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");

        Partida partida = gameCanvasService.getPartidaById(partidaId);
        Room room = gameCanvasService.getRoom(mapId, currentRoomId);



        return  "gameCanvas";
    }
}
