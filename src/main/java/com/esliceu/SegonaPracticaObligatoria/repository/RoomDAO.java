package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.Room;

public interface RoomDAO {
    Room get (String mapId, String currentRoomId);
    String getInitialRoomId(String mapId);
    Room getRoomByDirection(String map,String currentRoomId,String direction);
}