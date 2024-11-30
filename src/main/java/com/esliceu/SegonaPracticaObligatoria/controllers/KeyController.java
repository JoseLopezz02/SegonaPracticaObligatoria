package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KeyController {
    @Autowired
    GameCanvasService gameCanvasService;
    @GetMapping("/getKey")
    public String getKey(HttpSession session){
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");
        String partidaId = (String) session.getAttribute("partidaId");

        try {
            Room room = gameCanvasService.getRoom(mapId, currentRoomId);

            if (room.getKeyId() == null) {
                session.setAttribute("error", "No hay llaves en esta habitación.");
                return "gameCanvas";
            }


            return "gameCanvas";

        } catch (Exception e) {
            session.setAttribute("error", "Ocurrió un error inesperado.");
            return "gameCanvas";
        }
    }
}
