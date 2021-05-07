package com.terence.elevator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lift {
  private final int IDLE = 0;
  private final int MOVING = 1;

  private int status;
  private int targetFloor;
  private int currentFloor;
  private Direction direction;
  private boolean doorsOpen;
  private ExecutorService executor = Executors.newSingleThreadExecutor();

  private List<Boolean> buttonPanel;

  public Lift() {
    status = IDLE;
    targetFloor = 1;
    currentFloor = 1;
    direction = Direction.UP;

    buttonPanel = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      buttonPanel.add(i, Boolean.TRUE);
    }
  }

  // Button panel

  public void pressButton(int level) {
    buttonPanel.set(level - 1, Boolean.TRUE);
  }

  public void move(int level) {
    if (this.status == MOVING) {
      return;
    }

    executor.submit(() -> moveAsync(level));
  }

  int getNextLevelToDispatch(List<Boolean> levelsPressed, int currentFloor) {
    // Account for case where none pressed

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

  private void dispatchComplete() {
    // Find extremes of level that is closest to me
    // If no button is pressed, don't dispatch

    // If floors pressed only exist on one side of the current level, dispatch to the furthest level on that side

    // If floors are pressed on BOTH sides of the current level
    // Find lowest floor button that is pressed
    // Find highest floor button that is pressed

    // Can represent button panel as a list

    int newLevel = 1;
    executor.submit(() -> moveAsync(newLevel));
  }

  private void moveAsync(int level) {
    if (level == currentFloor) {
      return;
    }

    try {
      this.targetFloor = level;

      this.direction = this.currentFloor < level ? Direction.UP : Direction.DOWN;

      while (this.targetFloor != this.currentFloor) {
        currentFloor += this.direction.value;

        Thread.sleep(1000);
        System.out.println("Current floor: " + currentFloor);
        stopAtLevelIfNecessary();
      }

      dispatchComplete();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void stopAtLevelIfNecessary() throws InterruptedException {
    if (Boolean.TRUE.equals(buttonPanel.get(this.currentFloor - 1))) {
      this.doorsOpen = true;
      this.buttonPanel.set(this.currentFloor - 1, false);

      Thread.sleep(1000);
      System.out.println("Doors opening at: " + this.currentFloor);

      this.doorsOpen = false;
    }
  }
}
