package com.esliceu.SegonaPracticaObligatoria.model;

import java.util.List;

public class Room {
    int id;
    String name;
    Integer norte;
    Integer sur;
    Integer este;
    Integer oeste;
    int mapaId;
    Integer keyId;
    int coin;

    private List<Door> doors;
    private List<Llave> llaves;


    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public List<Llave> getLlaves() {
        return llaves;
    }

    public void setLlaves(List<Llave> llaves) {
        this.llaves = llaves;
    }

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

    public Integer getNorte() {
        return norte;
    }

    public void setNorte(Integer norte) {
        this.norte = norte;
    }

    public Integer getSur() {
        return sur;
    }

    public void setSur(Integer sur) {
        this.sur = sur;
    }

    public Integer getEste() {
        return este;
    }

    public void setEste(Integer este) {
        this.este = este;
    }

    public Integer getOeste() {
        return oeste;
    }

    public void setOeste(Integer oeste) {
        this.oeste = oeste;
    }

    public int getMapaId() {
        return mapaId;
    }

    public void setMapaId(int mapaId) {
        this.mapaId = mapaId;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
