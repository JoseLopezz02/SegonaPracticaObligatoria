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
            Room roomData = gameCanvasService.getRoom(mapId, currentRoomId);

            // Verifica si hay moneda
            if (roomData == null || roomData.getCoin() == 0) {
                session.setAttribute("error", "No hay monedas en esta habitación.");
                return "redirect:/gameCanvas";
            }

            // Actualiza estado de la partida
            Partida partida = gameCanvasService.getPartidaById(partidaId);
            int coinsCollected = partida.getCoinsCollected();
            model.addAttribute("coinsCollected", coinsCollected);

            gameCanvasService.updatePartidaWhereRoomHaveCoin(partidaId, currentRoomId);
            gameCanvasService.updateCountMonedasPartida(partidaId);

            return "redirect:/gameCanvas";
        } catch (Exception e) {
            session.setAttribute("error", "Ocurrió un error inesperado.");
            return "redirect:/gameCanvas";
        }
    }
}
