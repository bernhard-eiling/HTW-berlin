private class PowerUp {

  float xPos;
  float yPos;
  float xSpeed;
  float ySpeed;
  float xSize = 10;
  float ySize = 10;
  boolean hit = false;
  boolean active = false;
  boolean visibility = false;

  PowerUp(float xPos, float yPos, float xSpeed, float ySpeed) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.xSpeed = xSpeed;
    this.ySpeed = ySpeed;
  }

  void update() {
    if (visibility) {
      xPos += xSpeed;
      yPos += ySpeed;

      // PADDLE COLLISION
      if (active && yPos + ySize > paddle.getYPos() && xPos + xSize > paddle.getXPos() && xPos < paddle.getXPos() + paddle.getXSize()) {
        hit = true;
        active = false;
        this.visibility = false;
      }
    }
  }

  void display() {
    if (visibility) {
      noStroke();
      rect(xPos, yPos, xSize, ySize);
    }
  }

  void setActive(boolean active) {
    this.active = active;
  }
  boolean getHit() {
    return hit;
  }
  boolean getActive() {
    return active;
  }
  void setVisibility(boolean v) {
    this.visibility = v;
  }
  void setHit(boolean hit) {
    this.hit = hit;
  }
  void setXPos(float xPos) {
    this.xPos = xPos;
  }
  void setYPos(float yPos) {
    this.yPos = yPos;
  }
  void setXSpeed(float xSpeed) {
    this.xSpeed = xSpeed;
  }
  void setYSpeed(float ySpeed) {
    this.ySpeed = ySpeed;
  }
  boolean getVisibility() {
    return visibility;
  }
  float getXPos() {
    return this.xPos;
  }
  float getYPos() {
    return this.yPos;
  }
}

