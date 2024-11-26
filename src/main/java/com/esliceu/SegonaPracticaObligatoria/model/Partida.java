package com.esliceu.SegonaPracticaObligatoria.model;

import java.time.LocalDateTime;

public class Partida {
    int id;
    int userId;
    int coinsCollected;
    String keysCollected;
    int score;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    int currentRoomId;
    int idHabitacionMoneda; //habitacion que tiene moneda

    public int getIdHabitacionMoneda() {
        return idHabitacionMoneda;
    }

    public void setIdHabitacionMoneda(int idHabitacionMoneda) {
        this.idHabitacionMoneda = idHabitacionMoneda;
    }

    public int getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoomId(int currentRoomId) {
        this.currentRoomId = currentRoomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCoinsCollected() {
        return coinsCollected;
    }

    public void setCoinsCollected(int coinsCollected) {
        this.coinsCollected = coinsCollected;
    }

    public String getKeysCollected() {
        return keysCollected;
    }

    public void setKeysCollected(String keysCollected) {
        this.keysCollected = keysCollected;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
