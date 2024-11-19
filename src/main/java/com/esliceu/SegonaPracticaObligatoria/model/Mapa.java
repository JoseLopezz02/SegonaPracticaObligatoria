package com.esliceu.SegonaPracticaObligatoria.model;

import java.util.List;

public class Mapa {
    private int id;
    private String name;
    private int idRoomInicial;
    private List<Room> rooms;
    private List<Door> doors;
    private List<Llave> llaves;
    private List<Coin> coins;


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

    public int getIdRoomInicial() {
        return idRoomInicial;
    }

    public void setIdRoomInicial(int idRoomInicial) {
        this.idRoomInicial = idRoomInicial;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

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

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }
}
