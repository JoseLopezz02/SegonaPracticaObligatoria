package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.Mapa;
import com.esliceu.SegonaPracticaObligatoria.model.Room;
import com.esliceu.SegonaPracticaObligatoria.repository.GameCanvasDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameCanvasService {
    @Autowired
    GameCanvasDAO gameCanvasDAO;

    public Room getRoom(String mapId, String currentRoomId) {
        return gameCanvasDAO.get(mapId, currentRoomId);
    }

    public String convertDataToString(Room room) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
       return objectMapper.writeValueAsString(room);
    }

    public int getInitialRoomIdByMapId(String mapId) {
        return gameCanvasDAO.getInitialRoomId(mapId);
    }
}
