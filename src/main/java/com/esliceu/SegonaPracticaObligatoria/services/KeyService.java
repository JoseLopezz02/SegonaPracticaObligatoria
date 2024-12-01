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

    public void updatePartidaWhereRoomHaveKey(String partidaId, String currentRoomId, String idKey) {
        roomDAO.updateKeyPartida(partidaId,currentRoomId, idKey);
    }

    public Llave getKeyOfRoom(String currentRoomId) {
         return roomDAO.getKey(currentRoomId);
    }

}
