package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.repository.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinService {
    @Autowired
    RoomDAO roomDAO;

    public void updateCountMonedasPartida(String partidaId) {
        roomDAO.updateCountMonedas(partidaId);
    }

    public void updatePartidaWhereRoomHaveCoin(String partidaId, String currentRoomId) {
        roomDAO.updateCoinPartida(partidaId,currentRoomId);
    }
}