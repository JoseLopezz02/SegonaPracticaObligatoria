package com.esliceu.SegonaPracticaObligatoria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameCanvasController {

    @GetMapping("/gameCanvas")
    public String getGameCanvas(){
        return "gameCanvas";
    }
}
