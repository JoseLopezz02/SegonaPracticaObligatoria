package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.services.GameCanvasService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KeyController {
    @Autowired
    GameCanvasService gameCanvasService;
    @GetMapping("/getKey")
    public ResponseEntity<Object> getKey(HttpSession session){
        String mapId = (String) session.getAttribute("mapId");
        String currentRoomId = (String) session.getAttribute("currentRoomId");

        return null;
    }
}
