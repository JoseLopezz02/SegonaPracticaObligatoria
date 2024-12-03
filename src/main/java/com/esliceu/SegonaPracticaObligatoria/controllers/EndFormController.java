package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.services.ScoreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class EndFormController {
    @Autowired
    ScoreService scoreService;


    @GetMapping("/endform")
    public String getEndForm(){
        return "endform";
    }

    @PostMapping("/endform")
    public String postEndForm(@RequestParam("name") String name,
                              @RequestParam("comment") String comment, HttpSession session) {
       String partidaId = (String) session.getAttribute("partidaId");
       String mapId = (String) session.getAttribute("mapId");

        LocalDateTime finalTime = scoreService.getFinalTimeFromPartida(partidaId);
        LocalDateTime initialTime = scoreService.getInitialTimeFromPartida(partidaId);
        scoreService.saveScore(name, comment, finalTime, initialTime,mapId);

        // Redirigir a la página de scores
        return "redirect:/scores";
    }
}
