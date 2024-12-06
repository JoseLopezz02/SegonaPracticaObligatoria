package com.esliceu.SegonaPracticaObligatoria.services;

import com.esliceu.SegonaPracticaObligatoria.repository.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinService {
    @Autowired
    RoomDAO roomDAO;

    public void updateCountMonedasPartida(String partidaId, int monedas) {
        roomDAO.updateCountMonedas(partidaId,monedas);
    }

    public void updatePartidaWhereRoomHaveCoin(String partidaId, String currentRoomId) {
        roomDAO.updateCoinPartida(partidaId,currentRoomId);
    }

    public void restaCoinsCollected(String partidaId, int nuevasMonedas) {
        roomDAO.restaCoinsCollected(partidaId,nuevasMonedas);
    }
}
