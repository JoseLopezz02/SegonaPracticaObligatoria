package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.Door;
import com.esliceu.SegonaPracticaObligatoria.model.Mapa;
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

        map.put("norte", getDoorStatus(room.getNorte(), room.getDoors()));
        map.put("sur", getDoorStatus(room.getSur(), room.getDoors()));
        map.put("este", getDoorStatus(room.getEste(), room.getDoors()));
        map.put("oeste", getDoorStatus(room.getOeste(), room.getDoors()));

        boolean hayMoneda = room.getCoin() > 0 && monedaNoRecogida(partida,room.getId());
        map.put("coin", hayMoneda);

        map.put("roomName", room.getName());

        boolean hayLlave = room.getKeyId() != null && llaveNoRecogida(partida,room.getId());
        map.put("keys", hayLlave);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }

    private boolean llaveNoRecogida(Partida partida, int roomId) {
        if (partida.getIdHabitacionLlave() == null){
            return true;
        }
        String[] habitacionesConLlaves = partida.getIdHabitacionLlave().split(",");
        for (String id : habitacionesConLlaves) {
            if (id.equals(String.valueOf(roomId))) {
                return false;
            }
        }

        return true;
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
        if (targetRoom == null) {
            throw new IllegalArgumentException("No puedes atravesar la pared.");
        }
        return targetRoom;
    }

    public String createNewPartida(String userId, String mapName) {
        return roomDAO.createPartida(userId,mapName);
    }

    public void updateCurrentRoomPartida(String currentRoomId, String partidaId) {
        roomDAO.updateCurrentRoom(currentRoomId,partidaId);
    }

    public Partida getPartidaById(String partidaId) {
        return roomDAO.getPartida(partidaId);
    }

    public String getMapIdByName(String mapName) {
        return roomDAO.getMapIdByName(mapName);
    }

    public List<Mapa> getAllMaps() {
        return roomDAO.getMaps();
    }

    public Partida getActivePartidaForUser(String userId) {
        return roomDAO.getPartidaExistente(userId);
    }
}