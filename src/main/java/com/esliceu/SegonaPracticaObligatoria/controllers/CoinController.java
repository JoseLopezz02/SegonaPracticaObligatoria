package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoinController {
    @Autowired
    GameCanvasService gameCanvasService;

    @GetMapping("/getCoin")
    public String getCoin(HttpSession session, Model model) {
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");
        String partidaId = (String) session.getAttribute("partidaId");

        try {
            Room room = gameCanvasService.getRoom(mapId, currentRoomId);

            if (room.getCoin() == 0) {
                session.setAttribute("error", "No hay monedas en esta habitación.");
                return "gameCanvas";
            }

            // Actualiza estado de la partida
            gameCanvasService.updatePartidaWhereRoomHaveCoin(partidaId, currentRoomId);
            gameCanvasService.updateCountMonedasPartida(partidaId);

            // Vuelve a obtener la habitación actualizada
            Room updatedRoom = gameCanvasService.getRoom(mapId, currentRoomId);
            Partida partida = gameCanvasService.getPartidaById(partidaId);
            String updatedRoomData = gameCanvasService.convertDataToString(updatedRoom, partida);

            model.addAttribute("roomData", updatedRoomData);
            model.addAttribute("coinsCollected", partida.getCoinsCollected());

            return "gameCanvas";
        } catch (Exception e) {
            session.setAttribute("error", "Ocurrió un error inesperado.");
            return "gameCanvas";
        }
    }
}
