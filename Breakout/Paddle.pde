private class Paddle {

  float xSpeed = 5;
  float xPos;
  float yPos = height - height/10;
  float xSize;
  float ySize;
  boolean extended = false;
  int extensionTime = 400;
  int counter = 0;

  Paddle(float xPos, int xSize, int ySize) {
    this.xPos = xPos;
    this.xSize = xSize;
    this.ySize = ySize;
  }

  void update() {
    if (extended) {
      counter--;
      if (counter <= 0) {
        extended = false;
        this.xSize = this.xSize / 2;
      }
    }
  }

  void display() {
    fill(color(255, 255, 255));
    rect(xPos, yPos, xSize, ySize);
  }

  void extend() {
    this.xSize = this.xSize * 2;
    counter = extensionTime;
    extended = true;
  }

  void moveLeft() {
    if (xPos >= 0) {
      xPos -= xSpeed;
    }
  }
  void moveRight() {
    if (xPos + xSize < width) {
      xPos += xSpeed;
    }
  }
  void resetCounter() {
    counter = 0;
  }
  void setXSize(float xSize) {
    this.xSize = xSize;
  }
  void setXPos(float xPos) {
    this.xPos = xPos;
  }
  void setYPos(float yPos) {
    this.yPos = yPos;
  }
  void setExtended(boolean extended) {
    this.extended = extended;
  }
  boolean getExtended() {
    return extended;
  }
  float getXPos() {
    return xPos;
  }
  float getYPos() {
    return yPos;
  }
  float getXSize() {
    return xSize;
  }
  float getYSize() {
    return ySize;
  }
}

