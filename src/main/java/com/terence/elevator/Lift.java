package com.terence.elevator;

import com.terence.elevator.liftdispatchstrategy.LiftDispatchStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lift {
  private final int IDLE = 0;
  private final int MOVING = 1;
  private final LiftDispatchStrategy liftDispatchStrategy;

  private int status;
  private int targetFloor;
  private int currentFloor;
  private Direction direction;
  private ExecutorService executor = Executors.newSingleThreadExecutor();

  private List<Boolean> buttonPanel;

  public Lift(LiftDispatchStrategy liftDispatchStrategy) {
    this.liftDispatchStrategy = liftDispatchStrategy;
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
      dispatchLift(level);
    }
  }

  private void dispatchComplete() {
    try {
      int nextLevelToDispatch =
          liftDispatchStrategy.getNextLevelToDispatch(this.buttonPanel, this.currentFloor);
      System.out.println("Dispatching to next level: " + nextLevelToDispatch);
      dispatchLift(nextLevelToDispatch);
    } catch (com.terence.elevator.liftdispatchstrategy.NoDispatchException e) {
      this.status = IDLE;
    }
  }

  private void dispatchLift(int nextLevelToDispatch) {
    executor.submit(() -> moveAsync(nextLevelToDispatch));
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
      this.buttonPanel.set(this.currentFloor - 1, false);

      Thread.sleep(1000);
      System.out.println("Doors opening at: " + this.currentFloor);
    }
  }
}
