#include <Dice.h>

Dice dice;
int state = 0; 
int button = 3;

void setup()
{
}

void loop() {
  state = digitalRead(button);

  if (state == HIGH) {     
    dice.roll();
  }
}
