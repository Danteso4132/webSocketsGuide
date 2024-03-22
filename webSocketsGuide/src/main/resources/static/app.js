const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/greetings', (greeting) => {
        showGreeting(JSON.parse(greeting.body).content);
    });

    stompClient.subscribe('/number', (number) => {
        showNumber(JSON.parse(number.body).number); // Extract 'number' property
    });
    stompClient.subscribe('/intMatrix', (intMatrix) => {
        const matrix = JSON.parse(intMatrix.body);
        showIntMatrix(matrix);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

async function sendCellUpdate(x, y, type) {
    let colorCode;
    if (type == 'sand') {
        colorCode = '1';
    } else if (type == 'stone') {
        colorCode = '2';
    } else if (type == 'water') {
        colorCode = '3';
    } else if (type == 'clear') {
        colorCode = '0';
    }
    console.log('coloring cell ' + x + ':' + y + ' with color=' + colorCode);
    stompClient.publish({
        destination: "/app/drawOnCell",
        body: JSON.stringify({'x': x, 'y': y, 'colorCode': colorCode})
    });
}

let isMouseDown = false;

document.addEventListener('mousedown', () => {
    isMouseDown = true;
});

document.addEventListener('mouseup', () => {
    isMouseDown = false;
});

function handleBlockClick(event) {
    if (isMouseDown) {
        if (event.target.style.backgroundColor != 'chocolate' && event.target.style.backgroundColor != '#808080' && event.target.style.backgroundColor != '#00ffff') {

            if (enableSand) {
                event.target.style.backgroundColor = 'chocolate'; // Update color on mouse click
                console.log(event.target.getAttribute('data-row') + ":" + event.target.getAttribute('data-col'));
                sendCellUpdate(event.target.getAttribute('data-row'), event.target.getAttribute('data-col'), 'sand');
            }

            if (enableStone) {
                event.target.style.backgroundColor = '#808080';
                sendCellUpdate(event.target.getAttribute('data-row'), event.target.getAttribute('data-col'), 'stone');
            }

            if (enableWater) {
                event.target.style.backgroundColor = '#00ffff';
                sendCellUpdate(event.target.getAttribute('data-row'), event.target.getAttribute('data-col'), 'water');
            }
        }
        if (enableClear) {
            event.target.style.backgroundColor = 'darkgrey'; // Update color on mouse click
            sendCellUpdate(event.target.getAttribute('data-row'), event.target.getAttribute('data-col'), 'clear');
        }
    }
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function randomize() {
    console.log('Randomizing');
    stompClient.publish({
        destination: "/app/randomize",
        body: JSON.stringify({'name': '123'})
    });
}

function showGreeting(message) {
    console.log("showing greeting " + message);
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

function showNumber(number) {
    console.log("obtained random number = " + number);
}

function showIntMatrix(matrix) {
    console.log("Received integer matrix:");
    //console.log(matrix);
    for (let row = 0; row < totalRows; row++) {
        for (let col = 0; col < totalCols; col++) {
            const block = getBlockByRowCol(row, col);

            if (block) {
                if (matrix[row][col] == 1) {
                    block.style.backgroundColor = 'chocolate';
                } else if (matrix[row][col] == 2) {
                    block.style.backgroundColor = '#808080'
                }
                else if (matrix[row][col] == 3){
                    block.style.backgroundColor = '#00ffff'
                }
                else {
                    block.style.backgroundColor = '#aaa';
                }
                // Update the block with the new value or perform any other action
                // Add any other update logic here
            }
        }
    }
    // Process and display the matrix as needed
    // Here you can update your UI to render the matrix
}

function getBlockByRowCol(row, col) {
    return document.querySelector(`.block[data-row='${row}'][data-col='${col}']`);
}


$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#randomize").click(() => randomize());
});


