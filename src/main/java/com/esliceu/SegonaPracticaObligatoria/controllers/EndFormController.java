package com.esliceu.SegonaPracticaObligatoria.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EndFormController {
    @PostMapping("/endForm")
    public String postEndForm(){
        return "gameCanvas";
    }
}
