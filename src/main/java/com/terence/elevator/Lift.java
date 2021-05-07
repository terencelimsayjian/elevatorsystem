package com.terence.elevator;

import java.util.ArrayList;
import java.util.List;
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
      buttonPanel.add(i, Boolean.FALSE);
    }
  }

  public void pressButton(int level) {
    System.out.println("Setting level: " + level);
    System.out.println(buttonPanel);

    buttonPanel.set(level - 1, Boolean.TRUE);

    if (this.status == IDLE) {
      executor.submit(() -> moveAsync(level));
    }
  }

  public void move(int level) {
    if (this.status == MOVING) {
      return;
    }

    executor.submit(() -> moveAsync(level));
  }

  int getNextLevelToDispatch(List<Boolean> levelsPressed, int currentFloor) {
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
    int nextLevelToDispatch = getNextLevelToDispatch(this.buttonPanel, this.currentFloor);

    System.out.println("Dispatching to next level: " + nextLevelToDispatch);

    if (nextLevelToDispatch > 0) {
      executor.submit(() -> moveAsync(nextLevelToDispatch));
    } else {
      this.status = IDLE;
    }
  }

  private void moveAsync(int level) {
    if (level == currentFloor) {
      return;
    }

    this.status = MOVING;

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
