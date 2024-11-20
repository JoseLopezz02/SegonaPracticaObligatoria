package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Mapa;
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
public class GameCanvasController {
    @Autowired
    GameCanvasService gameCanvasService;
    @GetMapping("/gameCanvas")
    public String getGameCanvas(HttpSession session, Model model) throws JsonProcessingException {
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = session.getAttribute("currentRoomId").toString();

        System.out.println("Este es el id" + mapId);
        if (mapId == null) {
            model.addAttribute("error", "No se ha seleccionado un mapa.");
            return "redirect:/start";
        }
        Room room = gameCanvasService.getRoom(mapId, currentRoomId);
        if(room == null){
            model.addAttribute("error", "Mapa no encontrado.");
            return "start";
        }




        String roomData = gameCanvasService.convertDataToString(room);
        System.out.println(roomData);
        model.addAttribute("roomData", roomData);
        return "gameCanvas";
    }
}
