document.addEventListener('DOMContentLoaded', () => {
    const roomDataScript = document.getElementById('dataJson').textContent;
    let roomData;

    try {
        roomData = JSON.parse(roomDataScript);
        console.log("roomData recibido:", roomData);
    } catch (e) {
        console.error("Error al parsear roomData:", e);
        return;
    }

    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');
    const compass = document.getElementById('compass');
    const coinCountElement = document.getElementById('coinCount');
    const currentRoomElement = document.getElementById('currentRoom');
    const keysElement = document.getElementById('keys');

    const coin = new Image();
    coin.src = "/img/coin.gif";

    let coinsCollected = 0;

    // Función para verificar si el clic fue sobre la moneda
    const isCoinClicked = (x, y) => {
        const coinX = 50; // Posición X de la moneda
        const coinY = canvas.height - 80; // Posición Y de la moneda
        const coinWidth = 40;
        const coinHeight = 40;

        return x >= coinX && x <= coinX + coinWidth && y >= coinY && y <= coinY + coinHeight;
    };

    // Función para dibujar la habitación
    const drawRoom = () => {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Dibujar las paredes
        ctx.fillStyle = '#000';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        // Dibujar las puertas
        if (roomData.norte !== null) {
            ctx.fillStyle = roomData.doors.find(d => d.id === roomData.norte)?.open ? '#FFF' : '#F00';
            ctx.fillRect(canvas.width / 2 - 25, 0, 50, 10); // Puerta al norte
        }
        if (roomData.sur !== null) {
            ctx.fillStyle = roomData.doors.find(d => d.id === roomData.sur)?.open ? '#FFF' : '#F00';
            ctx.fillRect(canvas.width / 2 - 25, canvas.height - 10, 50, 10); // Puerta al sur
        }
        if (roomData.este !== null) {
            ctx.fillStyle = roomData.doors.find(d => d.id === roomData.este)?.open ? '#FFF' : '#F00';
            ctx.fillRect(canvas.width - 10, canvas.height / 2 - 25, 10, 50); // Puerta al este
        }
        if (roomData.oeste !== null) {
            ctx.fillStyle = roomData.doors.find(d => d.id === roomData.oeste)?.open ? '#FFF' : '#F00';
            ctx.fillRect(0, canvas.height / 2 - 25, 10, 50); // Puerta al oeste
        }

        // Dibujar la moneda si hay una
        if (roomData.coin !== 0) {
            const coinX = 50; // 50 píxeles desde el borde izquierdo
            const coinY = canvas.height - 80; // 80 píxeles desde el borde inferior
            ctx.drawImage(coin, coinX, coinY, 40, 40); // Dibuja la moneda
        }

        // Actualizar la información del juego
        currentRoomElement.textContent = roomData.name;
        coinCountElement.textContent = coinsCollected;
        keysElement.textContent = roomData.llaves.length ? roomData.llaves.join(', ') : 'Ninguna';
    };

    // Función para recoger la moneda
    const fetchGetCoin = () => {
        const getCoin = roomData.coin;
        if (getCoin === 0) {
            alert("No hay monedas en esta habitación.");
            return;
        }

        fetch(`/getCoin`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Error HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then((updatedRoomData) => {
                roomData = updatedRoomData; // Actualizar roomData con los datos del servidor
                coinsCollected++; // Incrementar el contador de monedas
                alert("¡Moneda recogida!");
                drawRoom(); // Redibujar la habitación después de recoger la moneda
            })
            .catch((err) => console.error("Error al coger la moneda:", err));
    };

    // Detectar clics en el canvas
    canvas.addEventListener('click', (event) => {
        const canvasRect = canvas.getBoundingClientRect();
        const x = event.clientX - canvasRect.left;
        const y = event.clientY - canvasRect.top;

        // Verificar si el clic fue sobre la moneda
        if (isCoinClicked(x, y)) {
            fetchGetCoin(); // Llamar a la función para recoger la moneda
        }
    });

    // Función para mover a la siguiente habitación
    const fetchNextRoom = (direction) => {
        const doorId = roomData[direction.toLowerCase()];
        if (!doorId) {
            alert("No puedes atravesar la pared.");
            return;
        }

        const door = roomData.doors.find(d => d.id === doorId);
        if (!door.open) {
            alert("La puerta está cerrada.");
            return;
        }

        // Realizar la solicitud al servidor
        fetch(`/nav?direction=${direction}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Error HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then((newRoomData) => {
                roomData = newRoomData;
                drawRoom(); // Redibujar la habitación después de moverse
            })
            .catch((err) => console.error("Error al cargar la nueva habitación:", err));
    };

    compass.addEventListener('click', (event) => {
        const compassRect = compass.getBoundingClientRect();
        const x = event.clientX - compassRect.left;
        const y = event.clientY - compassRect.top;

        // Determinar la dirección según el clic
        const centerX = compassRect.width / 2;
        const centerY = compassRect.height / 2;
        const angle = Math.atan2(y - centerY, x - centerX);

        if (angle > -Math.PI / 4 && angle <= Math.PI / 4) {
            fetchNextRoom('este'); // Este
        } else if (angle > Math.PI / 4 && angle <= 3 * Math.PI / 4) {
            fetchNextRoom('sur'); // Sur
        } else if (angle > -3 * Math.PI / 4 && angle <= -Math.PI / 4) {
            fetchNextRoom('norte'); // Norte
        } else {
            fetchNextRoom('oeste'); // Oeste
        }
    });

    drawRoom();
});