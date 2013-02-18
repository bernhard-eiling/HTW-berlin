private class PowerPaddle extends PowerUp {

  color c = color(255, 255, 0);
  float xPos;
  float yPos;
  float xSize = 8;
  float ySize = 8;
  
  
  PowerPaddle (float xPos, float yPos, float xSpeed, float ySpeed) {
    super(xPos, yPos, xSpeed, ySpeed);
    this.xPos = xPos;
    this.yPos = yPos;
  }
  
  void update() {
    super.update();
    if(super.getHit() && !paddle.getExtended()) {
      paddle.extend();
      super.setHit(false);
    }
  }
 
  void display() {
    fill(c);
    super.display();
  }
}
