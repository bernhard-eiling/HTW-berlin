import ddf.minim.*;

Minim minim;
AudioPlayer bounce;
AudioPlayer brick;
ArrayList<Ball> balls = new ArrayList<Ball>();
Paddle paddle;
Collision collision;
Logbook logbook;
PFont font;

PowerMulti multi;
PowerPaddle paddleP;
PowerSlow slow;

boolean moveL = false;
boolean moveR = false;
int lives = 3;
int ballSpeed = 5;
int bricksRow = 10;     // ROWS
int bricksCol = 5;      // COLUMNS
int disBricks = 10;     // DISTANT BETWEEN BRICKS
int xSizeBrick = 20;
int ySizeBrick = 10;
int paddleXSize = 100;
int paddleYSize = 10;
Brick[][] bricks = new Brick[bricksRow][bricksCol];
int state;

boolean startPlay;
boolean play;
boolean lost;
boolean lostLife;

void setup() {
  size(800, 600);

  // POWERUPS
  multi = new PowerMulti(width/2, 20, 0, 0);
  paddleP = new PowerPaddle(width/2, 20, 0, 0);
  slow = new PowerSlow(width/2, 20, 0, 0);

  // SOUND
  minim = new Minim(this);
  bounce = minim.loadFile("cartoon121.mp3");
  brick = minim.loadFile("ping.mp3");

  state = 1;          // START WITH BEGINN STATE

  font = createFont("Opificio", 32);
  textFont(font);

  //@Param (xPos, yPos, xSPeed, ySpeed, maxSpeed)
  balls.add(new Ball(width/2, height/2, 0, ballSpeed, ballSpeed));
  //@Param (float xPos, int xSize, int ySize)
  paddle = new Paddle(width/2 - paddleXSize/2, paddleXSize, paddleYSize);
  collision = new Collision(bricksRow, bricksCol);
  logbook = new Logbook(lives);
  createBricks();
}

void draw() {
  background(0);
  smooth();
  noStroke();

  // 1: Beginn, 2: Play, 3: Lost Life, 4: Lost Game, 5: Won Game
  switch(state) {
  case 1:
    displayStart();
    break;
  case 2:
    update();
    display();
    break;
  case 3:
    display();
    displayLostLife();
    break;
  case 4:
    displayLost();
    break;
  case 5: 
    displayWin();
    break;
  }
}

void displayStart() {
  fill(255);
  text("Press Key to Start", width/2, height/2);
}

void displayLost() {
  text("You lost. Press any key to start new game.", width/2, height/2);
}

void displayLostLife() {
  text("Lost Life. Press Key.", width/2, height/2);
}  

void displayWin() {
  text("Winning! Press Key to restart game.", width/2, height/2);
}

void update() {
  Iterator<Ball> b = balls.iterator();
  while (b.hasNext ()) {
    Ball currentBall = b.next();
    currentBall.update();
  }
  collision.check();
  paddle.update();
  controls();

  // UPDATING POWERUPS
  multi.update();
  paddleP.update();
  slow.update();
  // lOSING
  if (logbook.getLifes() <= 0) {
    state = 4;
  }
  // WINNING !
  if (logbook.getDestroyed() >= bricksRow * bricksCol) {
    state = 5;
  }
}

void display() {
  for (int i = 0; i < bricksRow; i++) {
    for (int k = 0; k < bricksCol; k++) {
      if (bricks[i][k].getVisibility()) {
        bricks[i][k].display();
      }
    }
  }
  Iterator<Ball> b = balls.iterator();
  while (b.hasNext ()) {
    Ball currentBall = b.next();
    currentBall.display();
  }
  paddle.display();
  logbook.display();
  multi.display();
  paddleP.display();
  slow.display();
}

void controls() {
  if (moveL) {
    paddle.moveLeft();
  } 
  else if (moveR) {
    paddle.moveRight();
  }
}

void createBricks() {
  for (int i = 0; i < bricksRow; i++) {
    for (int k = 0; k < bricksCol; k++) {
      // field of bricks gets always positioned in the middle of the screen
      bricks[i][k] = new Brick( i * (xSizeBrick + disBricks) + (width/2 - bricksRow/2 * (xSizeBrick + disBricks)), k * (ySizeBrick + disBricks) + height/10, xSizeBrick, ySizeBrick, int(random(1, 4)));
    }
  }
}

void reset() {
  for (int i = balls.size(); i > 0; i--) {
    balls.remove(i - 1);
  }
  balls.add(new Ball(width/2, height/2, 0, ballSpeed, ballSpeed));

  multi.setVisibility(false);
  paddleP.setVisibility(false);
  slow.setVisibility(false);

  moveL = false;
  moveR = false;

  logbook.resetLifes();
  logbook.resetScore();
  logbook.resetDestroyed();
  
  paddle.setXPos(width/2 - paddleXSize/2);
  paddle.setXSize(paddleXSize);
  paddle.setExtended(false);
  paddle.resetCounter();
  
  createBricks();
}

void keyPressed() {
  // 1: Beginn, 2: Play, 3: Lost Life, 4: Lost Game, 5: Won Game
  if (state == 2) {
    if (key == CODED) {
      if (keyCode == LEFT) {
        moveL = true;
      } 
      else if (keyCode == RIGHT) {
        moveR = true;
      }
    }
  } 
  else if (state == 1) {
    state = 2;
  } 
  else if (state == 5) {
    moveL = false;
    moveR = false;
    reset();
    state = 2;
  } 
  else if (state == 3) {
    moveL = false;
    moveR = false;
    state = 2;
  }
  else  if (state == 4) {
    moveL = false;
    moveR = false;
    reset();
    state = 2;
  }
}

void keyReleased() {
  if (state == 2) {
    if (key == CODED) {
      if (keyCode == LEFT) {
        moveL = false;
      } 
      else if (keyCode == RIGHT) {
        moveR = false;
      }
    }
  }
}

