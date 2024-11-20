package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.Mapa;
import com.esliceu.SegonaPracticaObligatoria.model.Room;

public interface GameCanvasDAO {
    Room get (String mapId, String currentRoomId);

    int getInitialRoomId(String mapId);
}
