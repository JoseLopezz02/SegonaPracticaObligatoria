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
    public String postEndForm(@RequestParam("comment") String comment, HttpSession session) {
        String username = (String) session.getAttribute("username");
       String partidaId = (String) session.getAttribute("partidaId");
       String mapName = (String) session.getAttribute("mapName");

        LocalDateTime finalTime = scoreService.getFinalTimeFromPartida(partidaId);
        LocalDateTime initialTime = scoreService.getInitialTimeFromPartida(partidaId);
        scoreService.saveScore(username, comment, finalTime, initialTime,mapName);

        return "redirect:/scores";
    }
}
