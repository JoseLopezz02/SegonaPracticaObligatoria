package com.esliceu.SegonaPracticaObligatoria.model;

import java.time.LocalDateTime;

public class Partida {
    int id;
    int userId;
    int coinsCollected;
    int score;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Integer currentRoomId;
    String idHabitacionMoneda;
    String idHabitacionLlave;

    public String getIdHabitacionLlave() {
        return idHabitacionLlave;
    }

    public void setIdHabitacionLlave(String idHabitacionLlave) {
        this.idHabitacionLlave = idHabitacionLlave;
    }

    public String getIdHabitacionMoneda() {
        return idHabitacionMoneda;
    }

    public void setIdHabitacionMoneda(String idHabitacionMoneda) {
        this.idHabitacionMoneda = idHabitacionMoneda;
    }

    public Integer getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoomId(Integer currentRoomId) {
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
