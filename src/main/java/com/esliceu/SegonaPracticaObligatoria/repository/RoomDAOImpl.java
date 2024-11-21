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
        Room room = jdbcTemplate.queryForObject(sqlRoom, new Object[]{currentRoomId, mapId},
                new BeanPropertyRowMapper<>(Room.class));

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

        String sqlLlaves = "SELECT * FROM Llave WHERE id IN (SELECT keyId FROM Room WHERE id = ? AND mapaId = ?)";
        List<Llave> llaves = jdbcTemplate.query(sqlLlaves, new Object[]{currentRoomId, mapId},
                new BeanPropertyRowMapper<>(Llave.class));
        room.setLlaves(llaves);

        return room;
    }

    public String getInitialRoomId(String mapId) {
        String sql = "SELECT idRoomInicial FROM Mapa WHERE id = ?";
        return String.valueOf(jdbcTemplate.queryForObject(sql, new Object[]{mapId}, Integer.class));
    }

    public Room getRoomByDirection(String mapId, String currentRoomId, String direction) {
        String sql = "SELECT * FROM Room WHERE id = (" +
                "  SELECT CASE " +
                "    WHEN ? = 'norte' THEN norte " +
                "    WHEN ? = 'sur' THEN sur " +
                "    WHEN ? = 'este' THEN este " +
                "    WHEN ? = 'oeste' THEN oeste " +
                "  END " +
                "  FROM Room WHERE id = ? AND mapaId = ?" +
                ")";
        //EL error esta aqui
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Room.class),
                direction, direction, direction, currentRoomId, mapId);
    }

}
