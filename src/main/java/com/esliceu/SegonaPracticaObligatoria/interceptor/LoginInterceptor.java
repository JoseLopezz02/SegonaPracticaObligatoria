package com.esliceu.SegonaPracticaObligatoria.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("username");
        if (user == null){
            //L'usuari no ha fet login
            response.sendRedirect("/login");
            return true;
        }
        //L'usuari si ha fet login
        return true;
    }
}
