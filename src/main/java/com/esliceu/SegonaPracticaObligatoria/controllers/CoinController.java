package com.esliceu.SegonaPracticaObligatoria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoinController {
    @GetMapping("/getCoin")
    public String getCoin(){
        return "hey";
    }
}
