package com.esliceu.SegonaPracticaObligatoria.model;

public class Llave {
    int id;
    int level;
    String nombre;
    int precioMonedas;
    int mapaId;
    int idRoom;

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecioMonedas() {
        return precioMonedas;
    }

    public void setPrecioMonedas(int precioMonedas) {
        this.precioMonedas = precioMonedas;
    }

    public int getMapaId() {
        return mapaId;
    }

    public void setMapaId(int mapaId) {
        this.mapaId = mapaId;
    }
}
