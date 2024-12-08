package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import com.esliceu.SegonaPracticaObligatoria.services.KeyService;
import com.esliceu.SegonaPracticaObligatoria.services.OpenDoorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OpenController {
    @Autowired
    OpenDoorService openDoorService;
    @Autowired
    GameCanvasService gameCanvasService;
    @Autowired
    KeyService keyService;

    @GetMapping("/open")
    public String openDoor(HttpSession session, Model model, @RequestParam String direction) {
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");
        String partidaId = (String) session.getAttribute("partidaId");

        try {
            Room room = gameCanvasService.getRoom(mapId, currentRoomId);
            Partida partida = gameCanvasService.getPartidaById(partidaId);
            String llavesRecogidas = keyService.recogerLlavesDeLaPartida(partidaId);

            if (openDoorService.canOpenDoor(room, partida, direction)) {

                openDoorService.openDoor(room, direction);

                String roomData = gameCanvasService.convertDataToString(room, partida);
                model.addAttribute("roomData", roomData);
                model.addAttribute("keysCollected", llavesRecogidas);

                model.addAttribute("message", "¡Puerta abierta con éxito!");
            } else {
                String roomData = gameCanvasService.convertDataToString(room, partida);
                model.addAttribute("roomData", roomData);
                model.addAttribute("keysCollected", llavesRecogidas);
                model.addAttribute("message", "No puedes abrir esta puerta. Quizás necesitas una llave.");
            }

            return "gameCanvas";

        } catch (Exception e) {
            session.setAttribute("error", "Ocurrió un error inesperado.");
            return "gameCanvas";
        }
    }


}
