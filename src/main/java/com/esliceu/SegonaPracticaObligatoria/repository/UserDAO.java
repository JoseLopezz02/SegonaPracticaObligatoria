package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.USER;

public interface UserDAO {
    void save(USER user);

    boolean comprobaSiUserExisteix(String username);

}
