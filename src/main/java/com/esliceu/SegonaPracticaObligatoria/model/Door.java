package com.esliceu.SegonaPracticaObligatoria.model;

public class Door {
    boolean isOpen;
    int habitacion1;
    int habitacion2;
    int id;
    Integer llaveId;
    int mapaId;
    int roomId;

    public boolean isOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public int getHabitacion1() {
        return habitacion1;
    }

    public void setHabitacion1(int habitacion1) {
        this.habitacion1 = habitacion1;
    }

    public int getHabitacion2() {
        return habitacion2;
    }

    public void setHabitacion2(int habitacion2) {
        this.habitacion2 = habitacion2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLlaveId() {
        return llaveId;
    }

    public void setLlaveId(Integer llaveId) {
        this.llaveId = llaveId;
    }

    public int getMapaId() {
        return mapaId;
    }

    public void setMapaId(int mapaId) {
        this.mapaId = mapaId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
