package com.esliceu.SegonaPracticaObligatoria.repository;

import java.time.LocalDateTime;

public interface ScoreDAO {

    void saveScore(String name, String comment, LocalDateTime finalTime,LocalDateTime initialTime, String mapId);

}
