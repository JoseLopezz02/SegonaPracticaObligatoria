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
    const currentRoomElement = document.getElementById('currentRoom');

    const coin = new Image();
    coin.src = "/img/coin.gif";
    const keyImg = new Image();
    keyImg.src = "/img/key.webp";
    const winImage = new Image();
    winImage.src = "/img/win.gif";
    const personaje = new Image();
    personaje.src = "/img/char_yellow.png";

    personaje.onload = () => {
        drawRoom();  // Dibuja la habitación y el personaje después de que la imagen esté cargada
    };

    // Tamaño de cada frame del sprite
    const spriteWidth = 100;
    const spriteHeight = 120;
    const framesPerRow = 4;

    // Estado del personaje (puede cambiar a 'quieto', 'caminando_derecha', 'caminando_izquierda', etc.)
    let estadoPersonaje = 'quieto';

    // Función para determinar las coordenadas del frame a dibujar
    const getFrameCoordinates = (estado) => {
        let frameX, frameY;

        switch (estado) {
            case 'quieto':
                frameX = 0;  // Primer frame en la primera fila
                frameY = 0;  // Fila 1
                break;
            case 'caminando_derecha':
                frameX = 1;  // Segundo frame en la primera fila
                frameY = 0;  // Fila 1
                break;
            case 'caminando_izquierda':
                frameX = 2;  // Tercer frame en la primera fila
                frameY = 0;  // Fila 1
                break;
            case 'saltando':
                frameX = 3;  // Cuarto frame en la primera fila
                frameY = 0;  // Fila 1
                break;
            // Agregar más estados según sea necesario
            default:
                frameX = 0;  // Default a la postura quieta
                frameY = 0;
                break;
        }

        return { frameX, frameY };
    };


    const drawPersonaje = () => {
        const x = (canvas.width - spriteWidth) / 2;
        const y = (canvas.height - spriteHeight) / 2;

        // Obtener las coordenadas del frame basado en el estado
        const { frameX, frameY } = getFrameCoordinates(estadoPersonaje);

        // Dibujar el frame correspondiente
        ctx.drawImage(personaje, frameX * spriteWidth, frameY * spriteHeight, spriteWidth, spriteHeight, x, y, spriteWidth, spriteHeight);
    };

    const isCoinClicked = (x, y) => {
        const coinX = 50;
        const coinY = canvas.height - 80;
        const coinWidth = 40;
        const coinHeight = 40;

        if (!roomData.coin) {
            return false;
        }

        return x >= coinX && x <= coinX + coinWidth && y >= coinY && y <= coinY + coinHeight;
    };

    const isKeyClicked = (x, y) => {
        const keyX = canvas.width - 90;
        const keyY = canvas.height - 80;
        const keyWidth = 40;
        const keyHeight = 40;

        if (!roomData.keys) {
            return false;
        }

        return x >= keyX && x <= keyX + keyWidth && y >= keyY && y <= keyY + keyHeight;
    };

    const drawRoom = () => {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        ctx.fillStyle = '#000';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        if (roomData.roomName === "habitacionFinal") {
            ctx.drawImage(winImage, canvas.width / 2 - 150, canvas.height / 2 - 150, 300, 300);
            return;
        }

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

        if (roomData.keys) {
            const keyX = canvas.width - 90;
            const keyY = canvas.height - 80;
            ctx.drawImage(keyImg, keyX, keyY, 40, 40);
        }

        drawPersonaje();
        currentRoomElement.textContent = roomData.roomName;
    };

    canvas.addEventListener('click', (event) => {
       const canvasRect = canvas.getBoundingClientRect();
       const x = event.clientX - canvasRect.left;
       const y = event.clientY - canvasRect.top;

       // Coordenadas y dimensiones de la imagen de victoria
       const winImageX = canvas.width / 2 - 150;
       const winImageY = canvas.height / 2 - 150;
       const winImageWidth = 300;
       const winImageHeight = 300;

       if (roomData.roomName === "habitacionFinal") {
           if (x >= winImageX && x <= winImageX + winImageWidth &&
               y >= winImageY && y <= winImageY + winImageHeight) {
               window.location.href = '/endform';
               return;
           }
       }

       if (isCoinClicked(x, y)) {
           window.location.href = '/getCoin';
           return;
       }

       if (isKeyClicked(x, y)) {
          window.location.href = '/getKey';
         // Actualizar la visibilidad de la llave inmediatamente
         roomData.keys = null;  // Eliminar la llave de los datos del cliente
         drawRoom();  // Redibujar la habitación sin la llave
         return;
       }
   });

    const navigateRoom = (direction) => {
        const doorStatus = roomData[direction];
        if (doorStatus === 'wall') {
            alert("No puedes atravesar una pared.");
            return;
        } else if (doorStatus === 'closed') {
            // Redirigir al controlador para abrir la puerta
            window.location.href = `/open?direction=${direction}`;
            return;
        }

        // Si la puerta está abierta, navegar normalmente
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
            estadoPersonaje = 'caminando_derecha';  // Actualizamos el estado al caminar a la derecha
            navigateRoom('este');
        } else if (angle > Math.PI / 4 && angle <= 3 * Math.PI / 4) {
            estadoPersonaje = 'caminando_derecha';
            navigateRoom('sur');
        } else if (angle > -3 * Math.PI / 4 && angle <= -Math.PI / 4) {
            estadoPersonaje = 'caminando_izquierda';
            navigateRoom('norte');
        } else {
            estadoPersonaje = 'caminando_izquierda';
            navigateRoom('oeste');
        }
    });
});