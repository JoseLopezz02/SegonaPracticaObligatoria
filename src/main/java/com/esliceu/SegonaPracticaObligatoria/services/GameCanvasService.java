package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.repository.RoomDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameCanvasService {
    @Autowired
    RoomDAO roomDAO;

    public Room getRoom(String mapId, String currentRoomId) {
        return roomDAO.get(mapId, currentRoomId);
    }

    public String convertDataToString(Room room) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
       return objectMapper.writeValueAsString(room);
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

    public void updateRoom(String mapId ,String currentRoomId) {
        roomDAO.updateCoinNumber(mapId, currentRoomId);
    }

    public String createNewPartida(String userId) {
        return roomDAO.createPartida(userId);
    }
}
