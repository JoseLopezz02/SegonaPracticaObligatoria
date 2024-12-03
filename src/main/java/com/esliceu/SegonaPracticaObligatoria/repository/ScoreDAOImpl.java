package com.esliceu.SegonaPracticaObligatoria.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ScoreDAOImpl implements ScoreDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public void saveScore(String name, String comment, LocalDateTime finalTime, LocalDateTime initialTime, String mapId) {

    }
}
