package com.esliceu.SegonaPracticaObligatoria.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Partida {
    int id;
    int userId;
    int coinsCollected;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    Integer currentRoomId;
    String idKeysCollected;
    String idHabitacionMoneda;
    String idHabitacionLlave;
    String idPuertasAbiertas;//Puertas que pasan de cerradas a abiertas
    String nombreLlaveCogida;
    String mapName;

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getNombreLlaveCogida() {
        return nombreLlaveCogida;
    }

    public void setNombreLlaveCogida(String nombreLlaveCogida) {
        this.nombreLlaveCogida = nombreLlaveCogida;
    }

    public String getIdPuertasAbiertas() {
        return idPuertasAbiertas;
    }

    public void setIdPuertasAbiertas(String idPuertasAbiertas) {
        this.idPuertasAbiertas = idPuertasAbiertas;
    }

    public String getIdKeysCollected() {
        return idKeysCollected;
    }

    public void setIdKeysCollected(String idKeysCollected) {
        this.idKeysCollected = idKeysCollected;
    }

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
