package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomDAO {
    Room get(String mapId, String currentRoomId);

    String getInitialRoomId(String mapId);

    Room getRoomByDirection(String map, String currentRoomId, String direction);

    String createPartida(String userId, String mapName);

    void updateCurrentRoom(String currentRoomId, String partidaId);

    void updateCoinPartida(String partidaId, String currentRoomId);

    void updateCountMonedas(String partidaId, int monedas);

    Partida getPartida(String partidaId);

    String getMapIdByName(String mapName);

    List<Mapa> getMaps();

    void updateKeyPartida(String partidaId, String currentRoomId, String keyName, String nombreLlave);

    Llave getKey(String currentRoomId);

    void restaCoinsCollected(String partidaId, int nuevasMonedas);

    String mostrarLlavesRecogidas(String partidaId);

    LocalDateTime getFinalTime(String partidaId);

    LocalDateTime getInitialTime(String partidaId);

    void resetLlaves(String partidaId);

    void resetMonedas(String partidaId);

    Partida getPartidaExistente(String userId, String mapName);

    void deletePartidaAcabada(String partidaId);
}
