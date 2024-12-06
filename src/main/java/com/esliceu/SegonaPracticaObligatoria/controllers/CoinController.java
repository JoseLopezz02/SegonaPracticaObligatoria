package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Llave;
import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.CoinService;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import com.esliceu.SegonaPracticaObligatoria.services.KeyService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoinController {
    @Autowired
    GameCanvasService gameCanvasService;
    @Autowired
    CoinService coinService;
    @Autowired
    KeyService keyService;

    @GetMapping("/getCoin")
    public String getCoin(HttpSession session, Model model) {
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");
        String partidaId = (String) session.getAttribute("partidaId");

        try {
            Room room = gameCanvasService.getRoom(mapId, currentRoomId);
            String llavesRecogidas = keyService.recogerLlavesDeLaPartida(partidaId);


            if (room.getCoin() == 0) {
                session.setAttribute("error", "No hay monedas en esta habitación.");
                return "gameCanvas";
            }

            int monedas = room.getCoin();
            // Actualiza estado de la partida
            coinService.updatePartidaWhereRoomHaveCoin(partidaId, currentRoomId);
            coinService.updateCountMonedasPartida(partidaId, monedas);

            // Vuelve a obtener la habitación actualizada
            Partida partida = gameCanvasService.getPartidaById(partidaId);
            String roomData = gameCanvasService.convertDataToString(room, partida);

            model.addAttribute("roomData", roomData);
            model.addAttribute("coinsCollected", partida.getCoinsCollected());
            model.addAttribute("keysCollected", llavesRecogidas);

            return "gameCanvas";
        } catch (Exception e) {
            session.setAttribute("error", "Ocurrió un error inesperado.");
            return "gameCanvas";
        }
    }
}
