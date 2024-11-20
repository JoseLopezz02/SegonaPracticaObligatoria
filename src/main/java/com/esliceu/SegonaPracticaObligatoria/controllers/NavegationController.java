package com.esliceu.SegonaPracticaObligatoria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NavegationController {
    @GetMapping("/nav")
    public String getNav(){
        return "";
    }
}
