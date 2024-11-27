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
    const keyImg = new Image();
    keyImg.src = "/img/key.webp";

    let coinsCollected = 0;

    const isCoinClicked = (x, y) => {
        const coinX = 50;
        const coinY = canvas.height - 80;
        const coinWidth = 40;
        const coinHeight = 40;

        return x >= coinX && x <= coinX + coinWidth && y >= coinY && y <= coinY + coinHeight;
    };

    const isKeyClicked = (x, y) => {
        const keyX = canvas.width - 90;
        const keyY = canvas.height - 80;
        const keyWidth = 40;
        const keyHeight = 40;

        return x >= keyX && x <= keyX + keyWidth && y >= keyY && y <= keyY + keyHeight;
    };

    const drawRoom = () => {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Dibujar las paredes
        ctx.fillStyle = '#000';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        // Dibujar las puertas según su estado
        if (roomData.north === 'open') {
            ctx.fillStyle = '#FFF';
            ctx.fillRect(canvas.width / 2 - 25, 0, 50, 10); // Puerta al norte
        } else if (roomData.north === 'closed') {
            ctx.fillStyle = '#F00';
            ctx.fillRect(canvas.width / 2 - 25, 0, 50, 10);
        }

        if (roomData.south === 'open') {
            ctx.fillStyle = '#FFF';
            ctx.fillRect(canvas.width / 2 - 25, canvas.height - 10, 50, 10); // Puerta al sur
        } else if (roomData.south === 'closed') {
            ctx.fillStyle = '#F00';
            ctx.fillRect(canvas.width / 2 - 25, canvas.height - 10, 50, 10);
        }

        if (roomData.east === 'open') {
            ctx.fillStyle = '#FFF';
            ctx.fillRect(canvas.width - 10, canvas.height / 2 - 25, 10, 50); // Puerta al este
        } else if (roomData.east === 'closed') {
            ctx.fillStyle = '#F00';
            ctx.fillRect(canvas.width - 10, canvas.height / 2 - 25, 10, 50);
        }

        if (roomData.west === 'open') {
            ctx.fillStyle = '#FFF';
            ctx.fillRect(0, canvas.height / 2 - 25, 10, 50); // Puerta al oeste
        } else if (roomData.west === 'closed') {
            ctx.fillStyle = '#F00';
            ctx.fillRect(0, canvas.height / 2 - 25, 10, 50);
        }

        if (roomData.coin) {
            const coinX = 50;
            const coinY = canvas.height - 80;
            ctx.drawImage(coin, coinX, coinY, 40, 40);
        }

        // Dibujar llave si hay
        if (roomData.keys.length > 0) {
            const keyX = canvas.width - 90;
            const keyY = canvas.height - 80;
            ctx.drawImage(keyImg, keyX, keyY, 40, 40);
        }

        currentRoomElement.textContent = roomData.roomName;
        coinCountElement.textContent = coinsCollected;
        keysElement.textContent = roomData.keys.length ? roomData.keys.join(', ') : 'Ninguna';
    };

    canvas.addEventListener('click', (event) => {
        const canvasRect = canvas.getBoundingClientRect();
        const x = event.clientX - canvasRect.left;
        const y = event.clientY - canvasRect.top;

        if (isCoinClicked(x, y)) {
            fetchGetCoin();
        }
        if (isKeyClicked(x, y)) {
            fetchGetKey();
        }
    });

    const fetchGetKey = () => {
        if (!roomData.keys.length) {
            alert("No hay llaves en esta habitación.");
            return;
        }

        fetch(`/getKey`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Error HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then((updatedRoomData) => {
                roomData = updatedRoomData;
                alert("¡Llave recogida!");
                drawRoom();
            })
            .catch((err) => console.error("Error al recoger la llave:", err));
    };

    const fetchGetCoin = () => {
        if (!roomData.coin) {
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
                roomData = updatedRoomData;
                coinsCollected++;
                alert("¡Moneda recogida!");
                drawRoom();
            })
            .catch((err) => console.error("Error al recoger la moneda:", err));
    };

    const fetchNextRoom = (direction) => {
        const doorStatus = roomData[direction];
        if (doorStatus === 'wall') {
            alert("No puedes atravesar una pared.");
            return;
        } else if (doorStatus === 'closed') {
            alert("La puerta está cerrada.");
            return;
        }

        fetch(`/nav?direction=${direction}`)
            .then((response) => {
                if (!response.ok) {
                    throw new Error(`Error HTTP: ${response.status}`);
                }
                return response.json();
            })
            .then((newRoomData) => {
                roomData = newRoomData;
                drawRoom();
            })
            .catch((err) => console.error("Error al cargar la nueva habitación:", err));
    };

    compass.addEventListener('click', (event) => {
        const compassRect = compass.getBoundingClientRect();
        const x = event.clientX - compassRect.left;
        const y = event.clientY - compassRect.top;

        const centerX = compassRect.width / 2;
        const centerY = compassRect.height / 2;
        const angle = Math.atan2(y - centerY, x - centerX);

        if (angle > -Math.PI / 4 && angle <= Math.PI / 4) {
            fetchNextRoom('east');
        } else if (angle > Math.PI / 4 && angle <= 3 * Math.PI / 4) {
            fetchNextRoom('south');
        } else if (angle > -3 * Math.PI / 4 && angle <= -Math.PI / 4) {
            fetchNextRoom('north');
        } else {
            fetchNextRoom('west');
        }
    });

    drawRoom();
});
