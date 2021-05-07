package com.terence.elevator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LiftTest {

  @Test
  void testMove() throws Exception {
    Lift lift = new Lift();

    lift.move(5);
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
  void testShouldGoDownToFarthestLevel() {
    Lift lift = new Lift();

    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, true);
    buttons.add(1, false);
    buttons.add(2, false);
    buttons.add(3, false);
    buttons.add(4, false);
    buttons.add(5, false);

    int currentLevel = 3;

    int nextLevelToDispatch = lift.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(1, nextLevelToDispatch);
  }

  @Test
  void testShouldGoDownToFarthestLevelDespiteHavingCloserLevel() {
    Lift lift = new Lift();

    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, true);
    buttons.add(2, true);
    buttons.add(3, false);
    buttons.add(4, false);
    buttons.add(5, false);

    int currentLevel = 3;

    int nextLevelToDispatch = lift.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(2, nextLevelToDispatch);
  }

  @Test
  void testShouldGoUpToFarthestLevel() {
    Lift lift = new Lift();

    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, false);
    buttons.add(2, false);
    buttons.add(3, false);
    buttons.add(4, false);
    buttons.add(5, true);

    int currentLevel = 3;

    int nextLevelToDispatch = lift.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(6, nextLevelToDispatch);
  }

  @Test
  void testShouldGoUpToFarthestLevelDespiteHavingCloserLevel() {
    Lift lift = new Lift();

    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, false);
    buttons.add(2, false);
    buttons.add(3, true);
    buttons.add(4, true);
    buttons.add(5, false);

    int currentLevel = 3;

    int nextLevelToDispatch = lift.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(5, nextLevelToDispatch);
  }

  @Test
  void testShouldGoToCloserLevelBelowIfUpAndDownAreOptions() {
    Lift lift = new Lift();

    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, true);
    buttons.add(2, false);
    buttons.add(3, false);
    buttons.add(4, true);
    buttons.add(5, false);

    int currentLevel = 3;

    int nextLevelToDispatch = lift.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(2, nextLevelToDispatch);
  }

  @Test
  void testShouldGoToCloserLevelAboveIfUpAndDownAreOptions() {
    Lift lift = new Lift();

    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, true);
    buttons.add(2, false);
    buttons.add(3, false);
    buttons.add(4, true);
    buttons.add(5, false);

    int currentLevel = 4;

    int nextLevelToDispatch = lift.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(5, nextLevelToDispatch);
  }

  @Test
  void testReturnMinusOneIfNoButtonsPressed() {
    Lift lift = new Lift();

    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, false);
    buttons.add(2, false);
    buttons.add(3, false);

    int currentLevel = 2;

    int nextLevelToDispatch = lift.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(-1, nextLevelToDispatch);
  }
}
