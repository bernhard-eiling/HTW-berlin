private class Ball {

  float xPos;
  float yPos;
  float dim = 10;
  float xSpeed;
  float ySpeed;
  float maxSpeed;
  float slowFactor = 0.5;
  float slowCounter = 0;
  float slowTime = 400;
  boolean slowed = false;

  Ball(float x, float y, float xS, float yS, float mS) {
    xPos = x;
    yPos = y;
    xSpeed = xS;
    ySpeed = yS;
    maxSpeed = mS;
  }

  void update() {
    //println("BallSpeed: " + mag(xSpeed, ySpeed));
    xPos += xSpeed;
    yPos += ySpeed;
    if (slowed) {
      slowCounter--;
      if (slowCounter <= 0) {
        slowed = false;
        this.xSpeed = this.xSpeed / slowFactor;
        this.ySpeed = this.ySpeed / slowFactor;
        this.maxSpeed = this.maxSpeed / slowFactor;
      }
    }
  }

  void display() {
    fill(color(255, 255, 255));
    ellipseMode(CORNER);
    ellipse(xPos, yPos, dim, dim);
    stroke(255);
  }

  void slow() {
    if (!slowed) {
    slowed = true;
    this.xSpeed = this.xSpeed * slowFactor;
    this.ySpeed = this.ySpeed * slowFactor;
    this.maxSpeed = this.maxSpeed * slowFactor;
    slowCounter = slowTime;
    }
  }

  void setXSpeed(float xSpeed) {
    this.xSpeed = xSpeed;
  }
  void setYSpeed(float ySpeed) {
    this.ySpeed = ySpeed;
  }
  void setXPos(float xPos) {
    this.xPos = xPos;
  }
  void setYPos(float yPos) {
    this.yPos = yPos;
  }
  void setMaxSpeed(float maxSpeed) {
    this.maxSpeed = maxSpeed;
  }

  float getXSpeed() {
    return xSpeed;
  }
  float getYSpeed() {
    return ySpeed;
  }
  float getXPos() {
    return xPos;
  }
  float getYPos() {
    return yPos;
  }
  float getDim() {
    return dim;
  }
  float getMaxSpeed() {
    return maxSpeed;
  }
}

