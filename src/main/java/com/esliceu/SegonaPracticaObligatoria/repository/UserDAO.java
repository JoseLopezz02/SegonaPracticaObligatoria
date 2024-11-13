package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.User;

public interface UserDAO {
    void save(User user);

    boolean comprobaSiUserExisteix(String username);

    User checkUserInDbUsingPasswAndUserName(String username, String password);
}
