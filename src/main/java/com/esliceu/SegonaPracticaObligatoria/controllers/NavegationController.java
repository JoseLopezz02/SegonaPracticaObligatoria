package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class NavegationController {
    @Autowired
    GameCanvasService gameCanvasService;
    @GetMapping("/nav")
    @ResponseBody
    public Room getNav(@RequestParam("direction") String direction, HttpSession session, Model model){
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");

        try {
            Room futureCurrentRoom = gameCanvasService.roomNavegacion(mapId, currentRoomId, direction);
            session.setAttribute("currentRoomId", String.valueOf(futureCurrentRoom.getId()));
            return futureCurrentRoom; // Devuelve JSON automáticamente
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
