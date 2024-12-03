package com.esliceu.SegonaPracticaObligatoria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScoreController {
    @GetMapping("/score")
    public String getScores(){

        return "score";
    }
}
