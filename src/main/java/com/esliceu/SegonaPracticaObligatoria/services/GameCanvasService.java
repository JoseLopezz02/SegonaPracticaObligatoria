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

import java.util.Arrays;
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

    public String createNewPartida(String userId) {
        return roomDAO.createPartida(userId);
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

    public String openDoor(String direction, String partidaId, String mapId, String currentRoomId) {
        Partida partida = roomDAO.getPartida(partidaId);
        Room room = roomDAO.get(mapId, currentRoomId);

        Door door = searchDoorByDirection(direction, room);

        if (door == null) {
            return "No hay puerta en esa dirección.";
        }

        if (!door.isOpen()) {
            if (hasKeyForDoor(partida, door)) {
                door.setIsOpen(true);
               // updateDoorState(door); // Actualizar en la base de datos
                System.out.println("La puerta se ha abierto.");
                return "La puerta se ha abierto.";
            } else {
                return "No tienes la llave para abrir esta puerta.";
            }
        } else {
            System.out.println("La puerta ya está abierta.");
            return "La puerta ya está abierta.";
        }
    }

    private Door searchDoorByDirection(String direction, Room room) {
        return switch (direction) {
            case "norte" -> getDoorByRoomId(room, room.getNorte());
            case "sur" -> getDoorByRoomId(room, room.getSur());
            case "este" -> getDoorByRoomId(room, room.getEste());
            case "oeste" -> getDoorByRoomId(room, room.getOeste());
            default -> null;
        };
    }

    private Door getDoorByRoomId(Room room, Integer doorId) {
        for (Door door : room.getDoors()) {
            if (door.getId() == doorId) {
                return door;
            }
        }
        return null;
    }

    private boolean hasKeyForDoor(Partida partida, Door door) {
        if (partida.getIdKeysCollected() == null || partida.getIdKeysCollected().isEmpty()) {
            return false;
        }

        // Convertimos el array a una lista para usar contains()
        List<String> keysCollected = Arrays.asList(partida.getIdKeysCollected().split(","));

        // Comprobar si la llave de la puerta está en la lista
        return keysCollected.contains(String.valueOf(door.getLlaveId()));
    }


}