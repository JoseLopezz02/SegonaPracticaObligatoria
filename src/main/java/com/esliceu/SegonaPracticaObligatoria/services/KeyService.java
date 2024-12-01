package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.model.Llave;
import com.esliceu.SegonaPracticaObligatoria.model.Partida;
import com.esliceu.SegonaPracticaObligatoria.repository.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyService {
    @Autowired
    RoomDAO roomDAO;

    public void updatePartidaWhereRoomHaveKey(String partidaId, String currentRoomId, String keyName) {
        roomDAO.updateKeyPartida(partidaId,currentRoomId, keyName);
    }

    public Llave getKeyOfRoom(String currentRoomId) {
         return roomDAO.getKey(currentRoomId);
    }

    public String getKeysInventario(String partidaId) {
        return roomDAO.getKeyInventario(partidaId);
    }
}
