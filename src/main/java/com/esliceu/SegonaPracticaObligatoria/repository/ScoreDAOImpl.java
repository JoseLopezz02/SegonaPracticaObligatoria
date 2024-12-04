package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
public class ScoreDAOImpl implements ScoreDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public void saveScore(String userName, String comment, LocalDateTime finalTime, LocalDateTime initialTime, String mapId) {
        String sql = "INSERT INTO Score (userName, comment, mapId, initialTime, finalTime, duration) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        long duration = java.time.Duration.between(initialTime, finalTime).getSeconds();
        jdbcTemplate.update(sql, userName, comment, mapId, initialTime, finalTime, duration);
    }

    @Override
    public List<Score> orderedByTime() {
        String sql = "SELECT * FROM Score ORDER BY duration ASC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Score.class));
    }

}
