package com.terence.elevator;

import com.terence.elevator.liftdispatchstrategy.ClosestExtremityStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LiftTest {
  Lift lift;


  @BeforeEach
  void setUp() {
    lift = new Lift(new ClosestExtremityStrategy());
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
}
