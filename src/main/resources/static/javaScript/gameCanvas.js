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
    };

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
            .then((response) => response.json())
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
