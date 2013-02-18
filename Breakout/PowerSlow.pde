private class PowerSlow extends PowerUp {

  color c = color(0, 0, 255);
  float xPos;
  float yPos;
  float xSize = 8;
  float ySize = 8;

  PowerSlow (float xPos, float yPos, float xSpeed, float ySpeed) {
    super(xPos, yPos, xSpeed, ySpeed);
    this.xPos = xPos;
    this.yPos = yPos;
  }

  void update() {
    super.update();
    if (super.getHit()) {
      Iterator<Ball> b = balls.iterator();
      while (b.hasNext ()) {
        Ball currentBall = b.next();
        currentBall.slow();
      }
      super.setHit(false);
    }
  }

  void display() {
    fill(c);
    super.display();
  }
}

