package com.esliceu.SegonaPracticaObligatoria.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class StartController {
    @GetMapping("/private")
    public String getPrivate(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("message", username);
        return "start";
    }
}
