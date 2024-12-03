package com.esliceu.SegonaPracticaObligatoria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScoreController {
    @GetMapping("/scores")
    public String getScores(){

        return "scores";
    }
}
