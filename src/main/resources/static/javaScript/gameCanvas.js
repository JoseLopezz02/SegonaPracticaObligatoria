document.addEventListener("DOMContentLoaded", () => {
    const canvas = document.getElementById('mazeCanvas');
    const context = canvas.getContext('2d');

    if (mapId === 'mazeForest') {
        drawForestMaze(context);
    } else if (mapId === 'mazeCave') {
        drawCaveMaze(context);
    } else {
        console.error('Mapa desconocido: ' + mapId);
    }
});

function drawForestMaze(context) {
    context.fillStyle = "#3a5f0b";
    context.fillRect(10, 10, 100, 100);

}

function drawCaveMaze(context) {
    context.fillStyle = "#4b4b4b";
    context.fillRect(10, 10, 100, 100);
}
