package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.Door;
import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.repository.RoomDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameCanvasService {
    @Autowired
    RoomDAO roomDAO;

    public Room getRoom(String mapId, String currentRoomId) {
        return roomDAO.get(mapId, currentRoomId);
    }

    public String convertDataToString(Room room, Partida partida) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();

        map.put("north", getDoorStatus(room.getNorte(), room.getDoors()));
        map.put("south", getDoorStatus(room.getSur(), room.getDoors()));
        map.put("east", getDoorStatus(room.getEste(), room.getDoors()));
        map.put("west", getDoorStatus(room.getOeste(), room.getDoors()));

        boolean hayMoneda = room.getCoin() > 0 && monedaNoRecogida(partida,room.getId());
        map.put("coin", hayMoneda);

        map.put("roomName", room.getName());
        //Hacer un boolean no hace falta mandar toda esa info
        map.put("keys", room.getLlaves());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }

    private String getDoorStatus(Integer doorId, List<Door> doors) {
        if (doorId == null) {
            return "wall";
        }

        Door door = doors.stream()
                .filter(d -> d.getId() == doorId)
                .findFirst()
                .orElse(null);

        if (door == null) {
            return "wall";
        }

        return door.isOpen() ? "open" : "closed";
    }


    private boolean monedaNoRecogida(Partida partida, int roomId) {
        if (partida.getIdHabitacionMoneda() == null) {
            return true;
        }
        String[] habitacionesConMonedas = partida.getIdHabitacionMoneda().split(",");
        for (String id : habitacionesConMonedas) {
            if (id.equals(String.valueOf(roomId))) {
                return false;
            }
        }
        return true;
    }

    public String  getInitialRoomIdByMapId(String mapId) {
        return roomDAO.getInitialRoomId(mapId);
    }

    public Room roomNavegacion(String mapId, String currentRoomId, String direction) {
        Room targetRoom = roomDAO.getRoomByDirection(mapId, currentRoomId, direction);
        System.out.println("Voy a la direccion : " + direction);
        System.out.println(targetRoom.getCoin());
        System.out.println(targetRoom.getDoors());
        System.out.println(targetRoom.getCoin());
        System.out.println(targetRoom.getId());
        if (targetRoom == null) {
            throw new IllegalArgumentException("No puedes atravesar la pared.");
        }
        return targetRoom;
    }

    public String createNewPartida(String userId) {
        return roomDAO.createPartida(userId);
    }

    public void updateCurrentRoomPartida(String currentRoomId, String partidaId) {
        roomDAO.updateCurrentRoom(currentRoomId,partidaId);
    }

    public void updatePartidaWhereRoomHaveCoin(String partidaId, String currentRoomId) {
        roomDAO.updateCoinPartida(partidaId,currentRoomId);
    }

    public void updateCountMonedasPartida(String partidaId) {
        roomDAO.updateCountMonedas(partidaId);
    }

    public Partida getPartidaById(String partidaId) {
        return roomDAO.getPartida(partidaId);
    }
}
