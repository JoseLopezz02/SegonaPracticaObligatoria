package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.USER;
import com.esliceu.SegonaPracticaObligatoria.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean addUser(String username, String name, String password) {
        if (userExists(username)){
            return false;
        }else {
            USER user = new USER();
            user.setUsername(username);
            user.setName(name);
            user.setPassword(password);

            userDAO.save(user);
          return true;
        }
    }

    private boolean userExists(String username) {
        return userDAO.comprobaSiUserExisteix(username);
    }

    public USER checkUser(String username, String password) {
       return userDAO.checkUserInDbUsingPasswAndUserName(username,password);
    }
}
