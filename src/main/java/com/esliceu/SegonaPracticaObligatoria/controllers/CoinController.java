package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoinController {
    @Autowired
    GameCanvasService gameCanvasService;

    @GetMapping("/getCoin")
    public ResponseEntity<Object> getCoin(HttpSession session){
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");
        String partidaId = (String) session.getAttribute("partidaId");

        try {
            Room roomData = gameCanvasService.getRoom(mapId, currentRoomId);

            if (roomData == null || roomData.getCoin() == 0) {
                return ResponseEntity.badRequest().body("No hay monedas en esta habitación.");
            }


            gameCanvasService.updatePartidaWhereRoomHaveCoin(partidaId, currentRoomId);
            gameCanvasService.updateCountMonedasPartida(partidaId);

            return ResponseEntity.accepted().body(roomData);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
