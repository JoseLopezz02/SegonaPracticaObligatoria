package com.esliceu.SegonaPracticaObligatoria.model;

public class Room {
    int id;
    String name;
    int norte;
    int sur;
    int este;
    int oeste;
    int mapaId;
    int key;
    int coin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNorte() {
        return norte;
    }

    public void setNorte(int norte) {
        this.norte = norte;
    }

    public int getSur() {
        return sur;
    }

    public void setSur(int sur) {
        this.sur = sur;
    }

    public int getEste() {
        return este;
    }

    public void setEste(int este) {
        this.este = este;
    }

    public int getOeste() {
        return oeste;
    }

    public void setOeste(int oeste) {
        this.oeste = oeste;
    }

    public int getMapaId() {
        return mapaId;
    }

    public void setMapaId(int mapaId) {
        this.mapaId = mapaId;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

}
