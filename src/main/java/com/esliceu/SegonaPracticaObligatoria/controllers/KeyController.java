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
public class KeyController {
    @Autowired
    GameCanvasService gameCanvasService;
    @Autowired
    KeyService keyService;
    @Autowired
    CoinService coinService;

    @GetMapping("/getKey")
    public String getKey(HttpSession session, Model model){
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");
        String partidaId = (String) session.getAttribute("partidaId");

        try {
            Room room = gameCanvasService.getRoom(mapId, currentRoomId);

            // Verificar si la habitación tiene una llave
            if (room.getKeyId() == null) {
                session.setAttribute("error", "No hay llaves en esta habitación.");
                return "gameCanvas";
            }

            // Obtener la llave de la habitación
            Llave llave = keyService.getKeyOfRoom(currentRoomId);
            Partida partida = gameCanvasService.getPartidaById(partidaId);

            if (llave.getPrecioMonedas() <= partida.getCoinsCollected()) {
                int cambioMonedas = partida.getCoinsCollected() - llave.getPrecioMonedas();
                coinService.restaCoinsCollected(partidaId, cambioMonedas);

                keyService.updatePartidaWhereRoomHaveKey(partidaId, currentRoomId, llave.getNombre());
                String roomData = gameCanvasService.convertDataToString(room, partida);

                model.addAttribute("roomData", roomData);
                model.addAttribute("coinsCollected", cambioMonedas);


                System.out.println("¡Llave obtenida con éxito!");
            } else {
                //Modificar para mostrar otro error
                System.out.println("No tienes suficientes monedas para obtener la llave.");
            }

            return "gameCanvas";

        } catch (Exception e) {
            session.setAttribute("error", "Ocurrió un error inesperado.");
            return "gameCanvas";
        }
    }

}
