#ifndef Dice_h
#define Dice_h

#include "Arduino.h"

class Dice
{
public:
  Dice(void);
  void showNumber(int number);
  void roll();
  
private:
  int state;
  int button;
  int pins[9];
};
#endif
