package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.Score;
import com.esliceu.SegonaPracticaObligatoria.repository.RoomDAO;
import com.esliceu.SegonaPracticaObligatoria.repository.ScoreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScoreService {
    @Autowired
    ScoreDAO scoreDAO;
    @Autowired
    RoomDAO roomDAO;

    public void saveScore(String username, String comment, LocalDateTime finalTime, LocalDateTime initialTime, String mapName) {
        scoreDAO.saveScore(username, comment, finalTime, initialTime, mapName);
    }

    public LocalDateTime getFinalTimeFromPartida(String partidaId) {
        return roomDAO.getFinalTime(partidaId);
    }

    public LocalDateTime getInitialTimeFromPartida(String partidaId) {
        return roomDAO.getInitialTime(partidaId);
    }

    public List<Score> getAllScoresOrderedByTime() {
        return scoreDAO.orderedByTime();
    }

    public void aseguraImpresioPodio(List<Score> scores) {
        while (scores.size() < 3) {
            scores.add(new Score("Vacío", "Sin comentario", null, null, 0L));
        }
    }
}
