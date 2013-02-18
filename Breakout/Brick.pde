private class Brick {
  
  float xPos;
  float yPos;
  float xSize;
  float ySize;
  int value;
  color c;
  boolean visibility = true;
  
  Brick(float x, float y, float xSize, float ySize, int v) {
    xPos = x;
    yPos = y;
    this.xSize = xSize;
    this.ySize = ySize;
    if(v == 1) {
      c = color(255, 255, 0);
      value = 1;
    } else if(v == 2) {
      c = color(0, 255, 0);
      value = 3;
    } else if(v == 3) {
      c = color(255, 0, 0);
      value = 7;
    }
  }

  void display() {
    fill(c);
    rect(xPos, yPos, xSize, ySize);
  }
  
  void setVisibility(boolean vis) {
    this.visibility = vis;
  }
  boolean getVisibility() {
    return visibility;
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
  int getValue() {
    return value;
  }
}
