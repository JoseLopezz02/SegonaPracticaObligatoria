package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.Score;

import java.time.LocalDateTime;
import java.util.List;

public interface ScoreDAO {

    void saveScore(String username, String comment, LocalDateTime finalTime, LocalDateTime initialTime, String mapId);

    List<Score> orderedByTime();
}
