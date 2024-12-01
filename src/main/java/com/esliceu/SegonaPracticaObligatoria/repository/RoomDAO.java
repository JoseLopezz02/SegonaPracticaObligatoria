package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.*;

import java.util.List;

public interface RoomDAO {
    Room get (String mapId, String currentRoomId);
    String getInitialRoomId(String mapId);
    Room getRoomByDirection(String map,String currentRoomId,String direction);
    String createPartida(String userId);
    void updateCurrentRoom(String currentRoomId, String partidaId);
    void updateCoinPartida(String partidaId, String currentRoomId);
    void updateCountMonedas(String partidaId);
    Partida getPartida(String partidaId);

    String getMapIdByName(String mapName);

    List<Mapa> getMaps();

    void updateKeyPartida(String partidaId, String currentRoomId, String keyName);

    Llave getKey(String currentRoomId);


    void restaCoinsCollected(String partidaId, int nuevasMonedas);

    void updateDoor(Door door);
}
