package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDAOImpl implements RoomDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Room get(String mapId, String currentRoomId) {
        String sqlRoom = "SELECT * FROM Room WHERE id = ? AND mapaId = ?";
        try {
            Room room = jdbcTemplate.queryForObject(sqlRoom, new Object[]{currentRoomId, mapId},
                    new BeanPropertyRowMapper<>(Room.class));
            getDoorsOfRoom(mapId, currentRoomId, room);
            getKeysOfRoom(mapId, currentRoomId, room);

            return room;
        }catch (Exception e){
            return null;
        }
    }

    private void getDoorsOfRoom(String mapId, String currentRoomId, Room room) {
        String sqlDoors = "SELECT * FROM Door WHERE (habitacion1 = ? OR habitacion2 = ?) AND mapaId = ?";
        List<Door> doors = jdbcTemplate.query(sqlDoors, new Object[]{currentRoomId, currentRoomId, mapId},
                (rs, rowNum) -> {
                    //RowMapper personalizado por errores con boolean al pasarlo de  la BD al Servidro
                    Door door = new Door();
                    door.setId(rs.getInt("id"));
                    door.setIsOpen(rs.getBoolean("isOpen"));
                    door.setHabitacion1(rs.getInt("habitacion1"));
                    door.setHabitacion2(rs.getInt("habitacion2"));
                    door.setLlaveId(rs.getInt("llaveId"));
                    door.setMapaId(rs.getInt("mapaId"));
                    door.setRoomId(rs.getInt("roomId"));
                    return door;
                });
        room.setDoors(doors);
    }

    public Room getRoomByDirection(String mapId, String currentRoomId, String direction) {
        String sql = "SELECT r.* FROM Room r " +
                "WHERE r.id = (" +
                "  SELECT CASE " +
                "    WHEN d.habitacion1 = ? THEN d.habitacion2 " +
                "    WHEN d.habitacion2 = ? THEN d.habitacion1 " +
                "    ELSE NULL " +
                "  END " +
                "  FROM Door d " +
                "  WHERE d.id = (" +
                "    SELECT " + direction + " " +
                "    FROM Room " +
                "    WHERE id = ? AND mapaId = ?" +
                "  )" +
                ")";
        try {
            Room targetRoom = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Room.class),
                    currentRoomId, currentRoomId, currentRoomId, mapId);
            String sqlDoors = "SELECT * FROM Door WHERE (habitacion1 = ? OR habitacion2 = ?) AND mapaId = ?";
            List<Door> doors = jdbcTemplate.query(sqlDoors, new Object[]{targetRoom.getId(), targetRoom.getId(), mapId},
                    (rs, rowNum) -> {
                        Door door = new Door();
                        door.setId(rs.getInt("id"));
                        door.setIsOpen(rs.getBoolean("isOpen"));
                        door.setHabitacion1(rs.getInt("habitacion1"));
                        door.setHabitacion2(rs.getInt("habitacion2"));
                        door.setLlaveId(rs.getInt("llaveId"));
                        door.setMapaId(rs.getInt("mapaId"));
                        door.setRoomId(rs.getInt("roomId"));
                        return door;
                    });
            targetRoom.setDoors(doors);
            getKeysOfRoom(mapId, currentRoomId, targetRoom);

            return targetRoom;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public String createPartida(String userId) {
        String sql = "INSERT INTO Partida (userId, coinsCollected, keysCollected, score, createdAt, updatedAt) " +
                "VALUES (?, 0, '', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql, userId);

        String getIdSql = "SELECT LAST_INSERT_ID()";
        return jdbcTemplate.queryForObject(getIdSql, String.class);
    }

    @Override
    public void updateCurrentRoom(String currentRoomId, String partidaId) {
        String sqlPartidaRoom = "UPDATE Partida SET currentRoomId = ? WHERE id = ?";
        jdbcTemplate.update(sqlPartidaRoom, currentRoomId, partidaId);
    }

    @Override
    public void updateCoinPartida(String partidaId, String currentRoomId) {
        String sql = "UPDATE Partida SET idHabitacionMoneda = CONCAT(COALESCE(idHabitacionMoneda, ''), ?, ',') WHERE id = ?";
        jdbcTemplate.update(sql, currentRoomId, partidaId);
    }

    @Override
    public void updateCountMonedas(String partidaId) {
        String sql = "UPDATE Partida SET coinsCollected = coinsCollected + 1 WHERE id = ?";
        jdbcTemplate.update(sql, partidaId);
    }

    private void getKeysOfRoom(String mapId, String currentRoomId, Room room) {
        String sqlLlaves = "SELECT * FROM Llave WHERE id IN (SELECT keyId FROM Room WHERE id = ? AND mapaId = ?)";
        List<Llave> llaves = jdbcTemplate.query(sqlLlaves, new Object[]{currentRoomId, mapId},
                new BeanPropertyRowMapper<>(Llave.class));
        room.setLlaves(llaves);
    }

    public String getInitialRoomId(String mapId) {
        String sql = "SELECT idRoomInicial FROM Mapa WHERE id = ?";
        return String.valueOf(jdbcTemplate.queryForObject(sql, new Object[]{mapId}, Integer.class));
    }
}
