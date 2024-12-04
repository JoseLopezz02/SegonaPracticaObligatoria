package com.esliceu.SegonaPracticaObligatoria.model;

import java.time.LocalDateTime;

public class Score {

    private int id;
    private String userName;
    private String comment;
    private LocalDateTime initialTime; // Corresponde a createdAt en Partida
    private LocalDateTime finalTime;   // Corresponde a updatedAt en Partida
    private String mapName;
    private long duration; // Tiempo total en segundos

    public Score(String userName, String comment, LocalDateTime initialTime, LocalDateTime finalTime, long duration) {
        this.userName = userName;
        this.comment = comment;
        this.initialTime = initialTime;
        this.finalTime = finalTime;
        this.duration = duration;
    }

    public Score() {
        // Constructor vacío necesario para Spring
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }


    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(LocalDateTime initialTime) {
        this.initialTime = initialTime;
    }

    public LocalDateTime getFinalTime() {
        return finalTime;
    }

    public void setFinalTime(LocalDateTime finalTime) {
        this.finalTime = finalTime;
    }
}


