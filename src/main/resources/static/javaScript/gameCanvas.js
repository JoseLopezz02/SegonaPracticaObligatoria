document.addEventListener('DOMContentLoaded', () => {
    const canvas = document.getElementById('canvas');
    const ctx = canvas.getContext('2d');

    const rooms = mapaData.rooms;
    const doors = mapaData.doors;
    const playerPosition = { x: 0, y: 0 };

    const images = {
        wall: new Image(),
        player: new Image(),
        compass: new Image(),
    };
    images.wall.src = '/img/wall.webp';
    images.player.src = '';
    images.compass.src = '/img/cross.webp';

    // Dibujar el mapa en el canvas
    images.wall.onload = () => {
        rooms.forEach(room => {
            ctx.drawImage(images.wall, room.x * 50, room.y * 50, 50, 50);
        });

        doors.forEach(door => {
            ctx.fillStyle = 'brown';
            ctx.fillRect(door.x * 50, door.y * 50, 50, 50);
        });


        ctx.drawImage(images.player, playerPosition.x, playerPosition.y, 50, 50);
    };

    document.addEventListener('keydown', (event) => {
        if (event.key === 'ArrowUp') playerPosition.y -= 50;
        if (event.key === 'ArrowDown') playerPosition.y += 50;
        if (event.key === 'ArrowLeft') playerPosition.x -= 50;
        if (event.key === 'ArrowRight') playerPosition.x += 50;

        // Redibujar el mapa con el jugador en su nueva posición
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        rooms.forEach(room => {
            ctx.drawImage(images.wall, room.x * 50, room.y * 50, 50, 50);
        });
        doors.forEach(door => {
            ctx.fillStyle = 'brown';
            ctx.fillRect(door.x * 50, door.y * 50, 50, 50);
        });
        ctx.drawImage(images.player, playerPosition.x, playerPosition.y, 50, 50);
    });
});
