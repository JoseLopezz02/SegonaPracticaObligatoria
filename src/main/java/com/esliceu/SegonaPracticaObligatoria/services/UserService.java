package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.User;
import com.esliceu.SegonaPracticaObligatoria.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addUser(String username, String name, String password) {
        String hashPasswd = hashPassword(password);
        if (userExists(username)) {
            return false;
        } else {
            User user = new User();
            user.setUsername(username);
            user.setName(name);
            user.setPassword(hashPasswd);

            userDAO.save(user);
            return true;
        }
    }

    private boolean userExists(String username) {
        return userDAO.comprobaSiUserExisteix(username);
    }

    public User checkUser(String username, String password) {
        String hashPasswd = hashPassword(password);
        return userDAO.checkUserInDbUsingPasswAndUserName(username, hashPasswd);
    }
}
