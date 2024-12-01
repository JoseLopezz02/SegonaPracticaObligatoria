package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Llave;
import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import com.esliceu.SegonaPracticaObligatoria.services.KeyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;

@Controller
public class NavegationController {
    @Autowired
    GameCanvasService gameCanvasService;
    @Autowired
    KeyService keyService;

    @GetMapping("/nav")

    public String getNav(@RequestParam("direction") String direction, HttpSession session, Model model){
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");
        String partidaId = (String) session.getAttribute("partidaId");

        try {
            Room futureCurrentRoom = gameCanvasService.roomNavegacion(mapId, currentRoomId, direction);
            gameCanvasService.updateCurrentRoomPartida(String.valueOf(futureCurrentRoom.getId()), partidaId);

            Partida partida = gameCanvasService.getPartidaById(partidaId);
            Llave llave = keyService.getKeyOfRoom(String.valueOf(futureCurrentRoom.getId()));


            String roomData = gameCanvasService.convertDataToString(futureCurrentRoom, partida);
            session.setAttribute("currentRoomId", String.valueOf(futureCurrentRoom.getId()));
            session.setAttribute("mapId", mapId);

            model.addAttribute("roomData", roomData);
            model.addAttribute("coinsCollected", partida.getCoinsCollected());
            // Maneja el caso donde no hay llave en la habitación con un model


            System.out.println("Habitacion actual" + roomData);

            return "gameCanvas";
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}