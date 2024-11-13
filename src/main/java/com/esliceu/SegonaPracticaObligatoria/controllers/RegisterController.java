package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;
    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }
    @PostMapping("/register")
    public String postRegister(HttpSession session,
                               @RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String name,
                               Model model) {
        boolean newUser = userService.addUser(username, name, password);

        if (newUser) {
            session.setAttribute("username", username);
            return "redirect:/login";
        } else {
            model.addAttribute("message", "El usuario ya existente");
            return "register";
        }
    }

}
