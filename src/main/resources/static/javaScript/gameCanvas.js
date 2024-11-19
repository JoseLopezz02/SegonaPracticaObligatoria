document.addEventListener('DOMContentLoaded', () => {
    const mapaDataScript = document.getElementById('dataJson').textContent;
    console.log("Contenido de mapaData:", mapaDataScript);  // Ver el contenido

    try {
        const mapaData = JSON.parse(mapaDataScript);
        console.log("mapaData recibido:", mapaData);
    } catch (e) {
        console.error("Error al parsear mapaData:", e);
    }

    const rooms = Array.isArray(mapaData.rooms) ? mapaData.rooms : [];
    const doors = Array.isArray(mapaData.doors) ? mapaData.doors : [];
    console.log("Rooms:", rooms);
    console.log("Doors:", doors);

    const playerPosition = { x: 0, y: 0 };

    const images = {
        wall: new Image(),
        player: new Image(),
        compass: new Image(),
    };

    images.wall.src = '/img/wall.webp';
    images.player.src = '/img/player.webp';
    images.compass.src = '/img/cross.webp';

    // Promesas para cargar las imágenes
    Promise.all([
        new Promise(resolve => images.wall.onload = resolve),
        new Promise(resolve => images.player.onload = resolve),
        new Promise(resolve => images.compass.onload = resolve),
    ]).then(() => {
        // Dibujar habitaciones y puertas solo si están definidos
        if (rooms.length > 0) {
            rooms.forEach((room, index) => {
                const x = room.x || index; // Usar índice como coordenada por defecto
                const y = room.y || index;
                ctx.drawImage(images.wall, x * 50, y * 50, 50, 50);
            });
        } else {
            console.error("Rooms está vacío o no es válido.");
        }

        if (doors.length > 0) {
            doors.forEach((door, index) => {
                const x = door.x || index; // Usar índice como coordenada por defecto
                const y = door.y || index;
                ctx.fillStyle = 'brown';
                ctx.fillRect(x * 50, y * 50, 50, 50);
            });
        } else {
            console.error("Doors está vacío o no es válido.");
        }

        // Dibujar jugador inicial
        ctx.drawImage(images.player, playerPosition.x, playerPosition.y, 50, 50);
    });

    // Controlar el movimiento del jugador
    document.addEventListener('keydown', (event) => {
        if (event.key === 'ArrowUp') playerPosition.y -= 50;
        if (event.key === 'ArrowDown') playerPosition.y += 50;
        if (event.key === 'ArrowLeft') playerPosition.x -= 50;
        if (event.key === 'ArrowRight') playerPosition.x += 50;

        // Redibujar el canvas con la nueva posición del jugador
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        if (rooms.length > 0) {
            rooms.forEach((room, index) => {
                const x = room.x || index;
                const y = room.y || index;
                ctx.drawImage(images.wall, x * 50, y * 50, 50, 50);
            });
        }

        if (doors.length > 0) {
            doors.forEach((door, index) => {
                const x = door.x || index;
                const y = door.y || index;
                ctx.fillStyle = 'brown';
                ctx.fillRect(x * 50, y * 50, 50, 50);
            });
        }

        ctx.drawImage(images.player, playerPosition.x, playerPosition.y, 50, 50);
    });
});
