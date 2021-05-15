package com.terence.elevator.liftdispatchstrategy;

import java.util.List;

public interface LiftDispatchStrategy {
  int getNextLevelToDispatch(List<Boolean> levelsPressed, int currentFloor) throws NoDispatchException;
}
