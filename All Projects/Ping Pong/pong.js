var court;
var ball;
var paddle;
var strikes;
var maxScore = 0;
var interval;
var ballX = 0;
var ballY = 0;
var ballwidth;
var ballVX = 5;
var ballVY = 5;
var courtX = 0;
var courtY = -80;
var courtheight = 775;
var courtwidth = 475;
var paddleX = 775;
var paddleY;
var paddleHeight = 100;
var message;
var speed;
var speedtype;
var selectedSpeed;
var gameOver;

function initialize(){
  court = document.getElementById("court");
  ball = document.getElementById("ball");
  paddle = document.getElementById("paddle");
  speedtype = document.getElementsByName("speed");
  strikes = 0;
  ballX = 0;
  ballY = 0;
  gameOver = false;
  ball.style.left = ballX + 'px';
  ball.style.top = ballY + 'px';
  speedtype[0].checked = true;

  document.getElementById("strikes").innerHTML = strikes;
  document.getElementById("score").innerHTML = maxScore;
  displayMessage(-1,-1);
}
function startGame(){
  ballwidth = ball.width;
  ballVX = 5;
  ballVY = 5;

  for(var i = 0; i < speedtype.length; i++) {
    if(speedtype[i].checked)
      selectedSpeed = speedtype[i].value;
  }
  setSpeed(selectedSpeed);
}
function setSpeed(level){
  if (level == 0) {
    interval = setInterval(frame,50);
  } else if (level == 1) {
    interval = setInterval(frame,40);
  } else if (level == 2) {
    interval = setInterval(frame,30);
  }
}
function frame(){
  if (ballX + ballVX < courtX) {
    ballVX = -ballVX;
  }
  if (ballY + ballVY < courtY || ballY + ballVY > courtY + courtwidth) {
    ballVY = -ballVY;
  }
  if (ballX + ballwidth + ballVX == courtX + courtheight && (paddleY < ballY + 80 && ballY + 80 < paddleY + paddleHeight)) {
    ballVX = -ballVX;
    strikes ++;
    document.getElementById("strikes").innerHTML = strikes;
    if (strikes > maxScore) {
      maxScore = strikes;
    }
    document.getElementById("score").innerHTML = maxScore;
  }
  if (ballX + ballwidth + ballVX > courtX + courtheight + 25) {
    gameOver = true;
    resetGame();
  }
  ballX += ballVX;
  ballY += ballVY;
  ball.style.left = ballX + 'px';
  ball.style.top = ballY + 'px';
  displayMessage(strikes, maxScore);
}
function resetGame(){
  if (gameOver) {
    ballX = 0;
    ballY = 0;
    clearInterval(interval);
    initialize();
    gameOver = false;
  }else {
    maxScore = 0;
    ballX = 0;
    ballY = 0;
    clearInterval(interval);
    initialize();
  }

}

var paddleYmin = 0;
var paddleYmax = 400;
function movePaddle(event){
    paddleY = event.pageY - 150;
    if (paddleY < paddleYmin){
      paddleY = paddleYmin;
    } else if (paddleY > paddleYmax){
      paddleY = paddleYmax;
    }
    paddle.style.top = paddleY + 'px';
    return true;
}

function displayMessage(strikes, maxScore){
  if (strikes == -1 && maxScore == -1) {
    msg = "Welcome to PONG developed by Ridham Kothari....."
  } else if (strikes == 0 && maxScore == 0) {
    msg = "Get ready for your first strike........"
  } else if (strikes == 0 && maxScore > 0) {
    msg = "Let's start again. Take a first strike to break High Scores......"
  } else if (strikes > 0 && strikes < maxScore) {
    var tempscore = maxScore - strikes;
    msg = "Strike "+ tempscore +" times to make new high score";
  } else if (strikes == maxScore) {
    msg = "You are the new high scorer. Build high enough that nobody can break your score"
  } else {
    msg = "Enjoy Pong... Happy Gaming...."
  }
  document.getElementById("messages").innerHTML = msg;
}
