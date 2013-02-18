private class Collision {

  int row;
  int col;
  float powerUpSpeed = 3;

  Collision(int row, int col) {
    this.row = row;
    this.col = col;
  }

  void check() {

    Iterator<Ball> b = balls.iterator();
    while (b.hasNext ()) {
      Ball currentBall = b.next();

      float xSpeed = currentBall.getXSpeed();
      float ySpeed = currentBall.getYSpeed();
      float xPos = currentBall.getXPos();
      float yPos = currentBall.getYPos();
      float dim = currentBall.getDim();
      float maxSpeed = currentBall.getMaxSpeed();

      float xPosP = paddle.getXPos();
      float yPosP = paddle.getYPos();
      float xSizeP = paddle.getXSize();

      // WALLS
      if (xPos < 0) {
        bounce.play();
        bounce.rewind();
        currentBall.setXSpeed(-xSpeed);
      }
      if (xPos + dim > width) {
        bounce.play();
        bounce.rewind();
        currentBall.setXSpeed(-xSpeed);
      }
      if (yPos < 0) {
        bounce.play();
        bounce.rewind();
        currentBall.setYSpeed(-ySpeed);
      }
      if (yPos > height) {
        state = 3;
        logbook.subLife();
        currentBall.setXPos(width/2);
        currentBall.setYPos(height/2);
        currentBall.setXSpeed(0);
        currentBall.setYSpeed(maxSpeed);
        paddle.setXPos(width/2 - paddle.getXSize()/2);
      }

      // PADDLE
      if (yPos + dim > yPosP && xPos + dim > xPosP && xPos < xPosP + xSizeP && ySpeed >= 0) {
        bounce.play();
        bounce.rewind();
        currentBall.setXSpeed(maxSpeed * cos( radians(45 + 90 * ((xPosP + xSizeP) - xPos) / xSizeP)));
        currentBall.setYSpeed(-maxSpeed * sin( radians(45 + 90 * ((xPosP + xSizeP) - xPos) / xSizeP)));
      }

      // BRICKS
      for (int i = 0; i < bricksRow; i++) {
        for (int k = 0; k < bricksCol; k++) {
          float xPosB = bricks[i][k].getXPos();
          float yPosB = bricks[i][k].getYPos();
          float xSizeB = bricks[i][k].getXSize();
          float ySizeB = bricks[i][k].getYSize();

          if (xPos + dim > xPosB && xPos < xPosB + xSizeB && yPos + dim > yPosB && yPos < yPosB + ySizeB) {
            brick.play();
            brick.rewind();
            if (bricks[i][k].getVisibility()) {
              if (xPos + dim > xPosB && xPos < xPosB + xSizeB && yPos + dim > yPosB && ySpeed >= 0) {
                currentBall.setYSpeed(-ySpeed);
              }
              if (xPos + dim > xPosB && xPos < xPosB + xSizeB && yPos < yPosB + ySizeB && ySpeed <= 0) {
                currentBall.setYSpeed(-ySpeed);
              }
              if (xPos + dim > xPosB && yPos + dim > yPosB && yPos < yPosB + ySizeB && xSpeed >= 0) {
                currentBall.setXSpeed(xSpeed);
              }
              if (xPos < xPosB + xSizeB && yPos + dim > yPosB && yPos < yPosB + ySizeB && xSpeed <= 0) {
                currentBall.setXSpeed(xSpeed);
              }
              logbook.addScore(bricks[i][k].getValue());
              // RELEASE POWERUP WHEN BRICK IS RED
              if (bricks[i][k].getValue() == 7) {
                int power = int(random(1, 4));
                //int power = 3;
                switch(power) {
                case 1:
                  if (!multi.getActive()) {
                    multi.setActive(true);
                    multi.setXPos(xPosB);
                    multi.setYPos(yPosB);
                    multi.setVisibility(true);
                    multi.setYSpeed(powerUpSpeed);
                  }
                  break;
                case 2: 
                  if (!paddleP.getActive()) {
                    paddleP.setActive(true);
                    paddleP.setXPos(xPosB);
                    paddleP.setYPos(yPosB);
                    paddleP.setVisibility(true);
                    paddleP.setYSpeed(powerUpSpeed);
                  }
                  break;
                case 3: 
                  if (!slow.getActive()) {
                    slow.setActive(true);
                    slow.setXPos(xPosB);
                    slow.setYPos(yPosB);
                    slow.setVisibility(true);
                    slow.setYSpeed(powerUpSpeed);
                  }
                  break;
                }
              }
              bricks[i][k].setVisibility(false);
            }
          }
        }
      }
    }
  }
}

