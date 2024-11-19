package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.Mapa;
import com.esliceu.SegonaPracticaObligatoria.repository.GameCanvasDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameCanvasService {
    @Autowired
    GameCanvasDAO gameCanvasDAO;

    public Mapa getMapa(String mapId) {
        return gameCanvasDAO.get(mapId);
    }

    public String convertDataToString(Mapa mapa) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
       return objectMapper.writeValueAsString(mapa);
    }
}
