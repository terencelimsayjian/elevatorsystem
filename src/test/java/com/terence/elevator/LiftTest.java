package com.terence.elevator;

import com.terence.elevator.liftdispatchstrategy.ClosestExtremityStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LiftTest {
  Lift lift;
  LiftDispatcher liftDispatcher;

  @BeforeEach
  void setUp() {
    lift = new Lift(new ClosestExtremityStrategy());

    List<Lift> liftList = new ArrayList<>();
    liftList.add(lift);

    liftDispatcher = new LiftDispatcher(liftList);
  }

  @Test
  void testMove() throws Exception {
    lift.pressButton(5);
    lift.pressButton(3);
    lift.pressButton(4);

    lift.pressButton(1);

    Thread.sleep(10000);
    lift.pressButton(4);

    Thread.sleep(10000);
    lift.pressButton(1);

    Thread.sleep(10000);
    assertTrue(true);
  }

  @Test
  void liftDispatch() throws Exception {

    liftDispatcher.pressButton(Direction.UP, 5);

    Thread.sleep(30000);
    assertTrue(true);
  }

}
