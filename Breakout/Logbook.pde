private class Logbook {
  
  int lifes;
  int maxLifes;
  int score;
  int bricksDestroyed;
  
  Logbook(int lifes) {
    this.lifes = lifes;
    maxLifes = lifes;
    score = 0;
    bricksDestroyed = 0;
  }
  
  void display() {
    fill(color(255, 255, 255));
    text("Life " + lifes, width - 120, 30);
    text("Score " + score, 10, 30);
  }
  
  void subLife() {
    lifes -= 1;
  }
  void addScore(int s) {
    score += s;
    bricksDestroyed++;
  }
  
  void resetDestroyed() {
    bricksDestroyed = 0;
  }
  int getDestroyed() {
    return bricksDestroyed;
  }
  int getLifes() {
    return lifes;
  }
  int getScore() {
    return score;
  }
  void resetLifes() {
    lifes = maxLifes;
  }
  void resetScore() {
    score = 0;
  }
}
