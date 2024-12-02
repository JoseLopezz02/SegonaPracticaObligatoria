package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.Door;
import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.repository.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OpenDoorService {

    public boolean canOpenDoor(Room room, Partida partida, String direction) {
        Door door = searchDoorByDirection(direction, room);

        return hasKeyForDoor(partida, door);
    }
    public void openDoor(Room room, String direction) {
        Door door = searchDoorByDirection(direction, room);

        if (door != null) {
            door.setIsOpen(true);
        }
    }

    private boolean hasKeyForDoor(Partida partida, Door door) {
        if (partida.getIdKeysCollected() == null || partida.getIdKeysCollected().isEmpty()) {
            return false;
        }

        List<String> keysCollected = Arrays.asList(partida.getIdKeysCollected().split(","));

        return keysCollected.contains(String.valueOf(door.getLlaveId()));
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
        if (doorId == null) return null;

        return room.getDoors().stream()
                .filter(door -> door.getId() == doorId)
                .findFirst()
                .orElse(null);
    }

}
