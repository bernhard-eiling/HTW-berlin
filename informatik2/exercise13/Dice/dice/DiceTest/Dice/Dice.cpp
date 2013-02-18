#include "Arduino.h"
#include "Dice.h"

Dice::Dice(void)
{
  state = 0; 
  button = 3; 
  
  pins[0] = 10;
  pins[1] = 7;
  pins[2] = 5;
  
  pins[3] = 11;
  pins[4] = 6;
  pins[5] = 4;
 
  pins[6] = 12;
  pins[7] = 9;
  pins[8] = 8;
  
  for(int i = 4; i < 13; i++) {
    pinMode(i, OUTPUT);
  }
  pinMode(button, INPUT);
}

void Dice::roll() {
  int number = random(1, 7);
  showNumber(number);
}

void Dice::showNumber(int number) {
  
  if(number == 1) {
    
    digitalWrite(pins[0], LOW);
    digitalWrite(pins[1], LOW);
    digitalWrite(pins[2], LOW);
    
    digitalWrite(pins[3], LOW);
    digitalWrite(pins[4], HIGH);
    digitalWrite(pins[5], LOW);
    
    digitalWrite(pins[6], LOW);
    digitalWrite(pins[7], LOW);
    digitalWrite(pins[8], LOW);
    
  } else if (number == 2) {
    
    digitalWrite(pins[0], HIGH);
    digitalWrite(pins[1], LOW);
    digitalWrite(pins[2], LOW);
    
    digitalWrite(pins[3], LOW);
    digitalWrite(pins[4], LOW);
    digitalWrite(pins[5], LOW);
    
    digitalWrite(pins[6], LOW);
    digitalWrite(pins[7], LOW);
    digitalWrite(pins[8], HIGH);
    
  } else if(number == 3) {
    
    digitalWrite(pins[0], HIGH);
    digitalWrite(pins[1], LOW);
    digitalWrite(pins[2], LOW);
    
    digitalWrite(pins[3], LOW);
    digitalWrite(pins[4], HIGH);
    digitalWrite(pins[5], LOW);
    
    digitalWrite(pins[6], LOW);
    digitalWrite(pins[7], LOW);
    digitalWrite(pins[8], HIGH);
    
  } else if(number == 4) {
    
    digitalWrite(pins[0], HIGH);
    digitalWrite(pins[1], LOW);
    digitalWrite(pins[2], HIGH);
    
    digitalWrite(pins[3], LOW);
    digitalWrite(pins[4], LOW);
    digitalWrite(pins[5], LOW);
    
    digitalWrite(pins[6], HIGH);
    digitalWrite(pins[7], LOW);
    digitalWrite(pins[8], HIGH);
    
  } else if(number == 5) {
    
    digitalWrite(pins[0], HIGH);
    digitalWrite(pins[1], LOW);
    digitalWrite(pins[2], HIGH);
    
    digitalWrite(pins[3], LOW);
    digitalWrite(pins[4], HIGH);
    digitalWrite(pins[5], LOW);
    
    digitalWrite(pins[6], HIGH);
    digitalWrite(pins[7], LOW);
    digitalWrite(pins[8], HIGH);
    
  } else if(number == 6) {
    
    digitalWrite(pins[0], HIGH);
    digitalWrite(pins[1], LOW);
    digitalWrite(pins[2], HIGH);
    
    digitalWrite(pins[3], HIGH);
    digitalWrite(pins[4], LOW);
    digitalWrite(pins[5], HIGH);
    
    digitalWrite(pins[6], HIGH);
    digitalWrite(pins[7], LOW);
    digitalWrite(pins[8], HIGH);
  }
}
