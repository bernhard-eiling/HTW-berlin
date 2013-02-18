private class PowerMulti extends PowerUp {

  color c = color(0, 255, 0);
  float xPos;
  float yPos;
  float xSize = 8;
  float ySize = 8;


  PowerMulti (float xPos, float yPos, float xSpeed, float ySpeed) {
    super(xPos, yPos, xSpeed, ySpeed);
    this.xPos = xPos;
    this.yPos = yPos;
  }

  void update() {
    super.update();
    if (super.getHit()) {
      if (balls.size() < 4) {
        balls.add(new Ball(width/2, height/2, 0, ballSpeed, ballSpeed));
      }
      super.setHit(false);
    }
  }

  void display() {
    fill(c);
    super.display();
  }
}

