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
        if (roomData.norte === 'open') {
            ctx.fillStyle = '#FFF';
            ctx.fillRect(canvas.width / 2 - 25, 0, 50, 10); // Puerta al norte
        } else if (roomData.norte === 'closed') {
            ctx.fillStyle = '#F00';
            ctx.fillRect(canvas.width / 2 - 25, 0, 50, 10);
        }

        if (roomData.sur === 'open') {
            ctx.fillStyle = '#FFF';
            ctx.fillRect(canvas.width / 2 - 25, canvas.height - 10, 50, 10); // Puerta al sur
        } else if (roomData.sur === 'closed') {
            ctx.fillStyle = '#F00';
            ctx.fillRect(canvas.width / 2 - 25, canvas.height - 10, 50, 10);
        }

        if (roomData.este === 'open') {
            ctx.fillStyle = '#FFF';
            ctx.fillRect(canvas.width - 10, canvas.height / 2 - 25, 10, 50); // Puerta al este
        } else if (roomData.este === 'closed') {
            ctx.fillStyle = '#F00';
            ctx.fillRect(canvas.width - 10, canvas.height / 2 - 25, 10, 50);
        }

        if (roomData.oeste === 'open') {
            ctx.fillStyle = '#FFF';
            ctx.fillRect(0, canvas.height / 2 - 25, 10, 50); // Puerta al oeste
        } else if (roomData.oeste === 'closed') {
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
            // Redirigir a la acción de recoger moneda
            window.location.href = '/getCoin';
        }
        if (isKeyClicked(x, y)) {
            // Redirigir a la acción de recoger llave
            window.location.href = '/getKey';
        }
    });

    const navigateRoom = (direction) => {
        const doorStatus = roomData[direction];
        if (doorStatus === 'wall') {
            alert("No puedes atravesar una pared.");
            return;
        } else if (doorStatus === 'closed') {
            alert("La puerta está cerrada.");
            return;
        }

        // Redirigir al servidor para cargar la nueva habitación
        window.location.href = `/nav?direction=${direction}`;
    };

    compass.addEventListener('click', (event) => {
        const compassRect = compass.getBoundingClientRect();
        const x = event.clientX - compassRect.left;
        const y = event.clientY - compassRect.top;

        const centerX = compassRect.width / 2;
        const centerY = compassRect.height / 2;
        const angle = Math.atan2(y - centerY, x - centerX);

        if (angle > -Math.PI / 4 && angle <= Math.PI / 4) {
            navigateRoom('este');
        } else if (angle > Math.PI / 4 && angle <= 3 * Math.PI / 4) {
            navigateRoom('sur');
        } else if (angle > -3 * Math.PI / 4 && angle <= -Math.PI / 4) {
            navigateRoom('norte');
        } else {
            navigateRoom('oeste');
        }
    });

    drawRoom();
});
