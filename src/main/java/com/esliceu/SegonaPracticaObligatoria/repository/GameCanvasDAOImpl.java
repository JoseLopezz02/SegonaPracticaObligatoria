package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GameCanvasDAOImpl implements GameCanvasDAO{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Room get(String mapId, String currentRoomId) {

        String sqlRoom = "SELECT * FROM Room WHERE id = ? AND mapaId = ?";
        Room room = jdbcTemplate.queryForObject(sqlRoom, new Object[]{currentRoomId, mapId},
                new BeanPropertyRowMapper<>(Room.class));

        String sqlDoors = "SELECT * FROM Door WHERE (habitacion1 = ? OR habitacion2 = ?) AND mapaId = ?";
        List<Door> doors = jdbcTemplate.query(sqlDoors, new Object[]{currentRoomId, currentRoomId, mapId},
                new RowMapper<Door>() { //Row mappers personalizado por problemas con boolean
                    @Override
                    public Door mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Door door = new Door();
                        door.setId(rs.getInt("id"));
                        door.setIsOpen(rs.getBoolean("isOpen"));
                        door.setHabitacion1(rs.getInt("habitacion1"));
                        door.setHabitacion2(rs.getInt("habitacion2"));
                        door.setLlaveId(rs.getInt("llaveId"));
                        door.setMapaId(rs.getInt("mapaId"));
                        door.setRoomId(rs.getInt("roomId"));

                        return door;
                    }
                });
        room.setDoors(doors);

        String sqlLlaves = "SELECT * FROM Llave WHERE id IN (SELECT llave FROM Room WHERE id = ? AND mapaId = ?)";
        List<Llave> llaves = jdbcTemplate.query(sqlLlaves, new Object[]{currentRoomId, mapId},
                new BeanPropertyRowMapper<>(Llave.class));
        room.setLlaves(llaves);

        String sqlCoins = "SELECT * FROM Coin WHERE mapaId = ? AND id IN (SELECT coin FROM Room WHERE id = ?)";
        List<Coin> coins = jdbcTemplate.query(sqlCoins, new Object[]{mapId, currentRoomId},
                new BeanPropertyRowMapper<>(Coin.class));
        room.setCoins(coins);

        return room;
    }

    public int getInitialRoomId(String mapId) {
        String sql = "SELECT idRoomInicial FROM Mapa WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{mapId}, Integer.class);
    }
}
