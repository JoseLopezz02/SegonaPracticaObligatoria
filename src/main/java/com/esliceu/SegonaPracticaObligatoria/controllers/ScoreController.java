package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.esliceu.SegonaPracticaObligatoria.services.ScoreService;

import java.util.List;

@Controller
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/scores")
    public String getScores(Model model) {
        List<Score> scores = scoreService.getAllScoresOrderedByTime();
        model.addAttribute("scores", scores);
        return "scores";
    }
}
