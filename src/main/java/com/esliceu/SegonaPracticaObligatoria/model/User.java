package com.esliceu.SegonaPracticaObligatoria.model;

import java.util.List;

public class User {
    String username;
    String name;
    int id;
    String password;
    //Hay que guardar el score del jugador, las llaves del mismo y las monedas


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
