document.addEventListener('DOMContentLoaded', () => {
    const roomDataScript = document.getElementById('dataJson').textContent;
    console.log("Contenido de la habitación:", roomDataScript);

    let roomData;
    try {
        roomData = JSON.parse(roomDataScript);
        console.log("roomData recibido:", roomData);
    } catch (e) {
        console.error("Error al parsear roomData:", e);
        return;
    }

    // Inicializar el canvas y las imágenes
    const canvas = document.getElementById('gameCanvas');
    const ctx = canvas.getContext('2d');
    const images = {
        wall: new Image(),
        player: new Image(),
        door: new Image(),
    };

    images.wall.src = '/img/wall.webp';
    images.player.src = '/img/char_yellow.png';
    images.door.src = '/img/door.webp';

    const playerPosition = { x: 0, y: 0 };

    const drawRoom = () => {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Dibujar las paredes (simples como fondo)
        ctx.drawImage(images.wall, 0, 0, canvas.width, canvas.height);

        roomData.doors.forEach((door) => {
            let x = 0;
            let y = 0;
            // Colocar puertas en base a las coordenadas de la habitación
            if (roomData.norte === door.id) {
                x = canvas.width / 2 - 25;
                y = 0;
            } else if (roomData.sur === door.id) {
                x = canvas.width / 2 - 25;
                y = canvas.height - 50;
            } else if (roomData.este === door.id) {
                x = canvas.width - 50;
                y = canvas.height / 2 - 25;
            } else if (roomData.oeste === door.id) {
                x = 0;
                y = canvas.height / 2 - 25;
            }
            ctx.drawImage(images.door, x, y, 50, 50);
        });

        ctx.drawImage(images.player, playerPosition.x, playerPosition.y, 50, 50);
    };

    // Cargar imágenes y dibujar por primera vez
    Promise.all([
        new Promise((resolve) => (images.wall.onload = resolve)),
        new Promise((resolve) => (images.player.onload = resolve)),
        new Promise((resolve) => (images.door.onload = resolve)),
    ]).then(drawRoom);

    const movePlayer = (direction) => {
        switch (direction) {
            case 'ArrowUp':
                if (roomData.norte) playerPosition.y -= 50;
                break;
            case 'ArrowDown':
                if (roomData.sur) playerPosition.y += 50;
                break;
            case 'ArrowLeft':
                if (roomData.oeste) playerPosition.x -= 50;
                break;
            case 'ArrowRight':
                if (roomData.este) playerPosition.x += 50;
                break;
        }
        drawRoom();
        fetchNextRoom(direction);
    };

   //Modificar este fetch para enviar bien la info al server
    const fetchNextRoom = (direction) => {
        const nextRoomId = roomData[direction.toLowerCase()];
        if (!nextRoomId) return;

        fetch(`/nav/${nextRoomId}`)
            .then((response) => response.json())
            .then((newRoomData) => {
                roomData = newRoomData;
                drawRoom();
            })
            .catch((err) => console.error("Error al cargar la nueva habitación:", err));
    };

    document.addEventListener('keydown', (event) => {
        movePlayer(event.key);
    });
});