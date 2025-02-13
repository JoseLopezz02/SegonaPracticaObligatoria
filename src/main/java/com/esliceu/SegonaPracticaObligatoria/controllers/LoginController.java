package com.esliceu.SegonaPracticaObligatoria.controllers;

import com.esliceu.SegonaPracticaObligatoria.model.User;
import com.esliceu.SegonaPracticaObligatoria.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(HttpSession session, @RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.checkUser(username, password);

        if (user != null) {
            session.setAttribute("username", username);
            session.setAttribute("userId", user.getId());
            return "redirect:/start";
        } else {
            model.addAttribute("message", "El usuario y/o contraseña son incorrectos");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
