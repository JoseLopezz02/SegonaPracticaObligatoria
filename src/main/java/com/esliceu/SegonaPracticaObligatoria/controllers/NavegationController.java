package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NavegationController {
    @Autowired
    GameCanvasService gameCanvasService;
    @GetMapping("/nav")
    public String getNav(@RequestParam("direction") String direction, HttpSession session, Model model){
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");

        try {
            Room futureCurrentRoom = gameCanvasService.roomNavegacion(mapId, currentRoomId, direction);
            session.setAttribute("currentRoomId", String.valueOf(futureCurrentRoom.getId()));
            String roomData = gameCanvasService.convertDataToString(futureCurrentRoom);

            System.out.println(futureCurrentRoom.getId());
            System.out.println("Esta es la futura room data" + roomData);

            model.addAttribute("roomData", roomData);
            return "gameCanvas";
        } catch (IllegalArgumentException | JsonProcessingException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
