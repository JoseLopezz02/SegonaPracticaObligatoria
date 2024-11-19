package com.esliceu.SegonaPracticaObligatoria.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class StartController {
    @GetMapping("/start")
    public String getStart(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("message", username);
        return "start";
    }
    @PostMapping("/start")
    public String postStart(@RequestParam String mapId, Model model,HttpSession session){
        if ("mazeForest".equals(mapId)){
            model.addAttribute("mapType", "forest");
        }else {
            model.addAttribute("mapType", "cave");
        }
        session.setAttribute("mapId", mapId);
        //Lanzar exception si el user cambia el nombre del mapa o el id del mapa
        return "redirect:/gameCanvas";
    }
}
