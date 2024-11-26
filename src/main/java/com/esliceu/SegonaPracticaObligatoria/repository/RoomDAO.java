package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.Room;

public interface RoomDAO {
    Room get (String mapId, String currentRoomId);
    String getInitialRoomId(String mapId);
    Room getRoomByDirection(String map,String currentRoomId,String direction);
    String createPartida(String userId);
    void updateCurrentRoom(String currentRoomId, String partidaId);
    void updateCoinPartida(String partidaId, String currentRoomId);
    void updateCountMonedas(String partidaId);
}
