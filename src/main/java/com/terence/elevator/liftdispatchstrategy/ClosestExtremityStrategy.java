package com.terence.elevator.liftdispatchstrategy;

import java.util.List;

public class ClosestExtremityStrategy implements LiftDispatchStrategy {
  @Override
  public int getNextLevelToDispatch(List<Boolean> levelsPressed, int currentFloor) {
    int lowestFloor = -1;
    for (int i = 0; i < currentFloor - 1; i++) {
      if (Boolean.TRUE.equals(levelsPressed.get(i))) {
        lowestFloor = i + 1;
        break;
      }
    }

    int highestFloor = -1;
    for (int i = levelsPressed.size() - 1; i > currentFloor - 1; i--) {
      if (Boolean.TRUE.equals(levelsPressed.get(i))) {
        highestFloor = i + 1;
        break;
      }
    }

    if (lowestFloor >= 0 && highestFloor >= 0) {
      return highestFloor - currentFloor > currentFloor - lowestFloor ? lowestFloor : highestFloor;
    } else if (lowestFloor < 0) {
      // only have higher floors
      return highestFloor;
    } else if (highestFloor < 0) {
      // only have lower floors
      return lowestFloor;
    }

    return -1;
  }
}
