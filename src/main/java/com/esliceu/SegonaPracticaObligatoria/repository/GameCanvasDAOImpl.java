package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GameCanvasDAOImpl implements GameCanvasDAO{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Mapa get(String mapId) {
        //Cambiar para solo mandar solo la informacion de una habitacion
        String sqlMapa = "SELECT * FROM Mapa WHERE id = ?";
        Mapa mapa = jdbcTemplate.queryForObject(sqlMapa, new Object[]{mapId},
                new BeanPropertyRowMapper<>(Mapa.class));

        String sqlRooms = "SELECT * FROM Room WHERE mapaId = ?";
        List<Room> rooms = jdbcTemplate.query(sqlRooms, new Object[]{mapId},
                new BeanPropertyRowMapper<>(Room.class));
        mapa.setRooms(rooms);

        String sqlDoors = "SELECT * FROM Door WHERE mapaId = ?";
        List<Door> doors = jdbcTemplate.query(sqlDoors, new Object[]{mapId},
                new BeanPropertyRowMapper<>(Door.class));
        mapa.setDoors(doors);

        String sqlLlaves = "SELECT * FROM Llave WHERE mapaId = ?";
        List<Llave> llaves = jdbcTemplate.query(sqlLlaves, new Object[]{mapId},
                new BeanPropertyRowMapper<>(Llave.class));
        mapa.setLlaves(llaves);

        String sqlCoins = "SELECT * FROM Coin WHERE mapaId = ?";
        List<Coin> coins = jdbcTemplate.query(sqlCoins, new Object[]{mapId},
                new BeanPropertyRowMapper<>(Coin.class));
        mapa.setCoins(coins);

        return mapa;
    }
}
