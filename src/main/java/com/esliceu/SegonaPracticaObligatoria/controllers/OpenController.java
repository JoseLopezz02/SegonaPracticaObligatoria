package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OpenController {
    @Autowired
    GameCanvasService gameCanvasService;
        @GetMapping("/open")
        public String getOpen(HttpSession session, @RequestParam String direction) {
            String mapId = (String) session.getAttribute("mapId");
            String partidaId = (String) session.getAttribute("partidaId");
            String currentRoomId = (String) session.getAttribute("currentRoomId");

            try {
                // Llamar al servicio para abrir la puerta
                String message = gameCanvasService.openDoor(direction, partidaId, mapId, currentRoomId);

                // Establecer el mensaje en el modelo para que se muestre en la vista
                session.setAttribute("message", message);

                return "gameCanvas";
            } catch (Exception e) {
                session.setAttribute("error", "Hubo un error al intentar abrir la puerta.");
                return "gameCanvas";
            }
        }
    }
