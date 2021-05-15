package com.terence.elevator.liftdispatchstrategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClosestExtremityStrategyTest {

  LiftDispatchStrategy strategy;

  @BeforeEach
  void setUp() {
    strategy = new ClosestExtremityStrategy();
  }

  @Test
  void testShouldGoDownToFarthestLevel() throws Exception {
    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, true);
    buttons.add(1, false);
    buttons.add(2, false);
    buttons.add(3, false);
    buttons.add(4, false);
    buttons.add(5, false);

    int currentLevel = 3;

    int nextLevelToDispatch = 0;
    nextLevelToDispatch = strategy.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(1, nextLevelToDispatch);
  }

  @Test
  void testShouldGoDownToFarthestLevelDespiteHavingCloserLevel() throws Exception {
    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, true);
    buttons.add(2, true);
    buttons.add(3, false);
    buttons.add(4, false);
    buttons.add(5, false);

    int currentLevel = 3;

    int nextLevelToDispatch = 0;
    nextLevelToDispatch = strategy.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(2, nextLevelToDispatch);
  }

  @Test
  void testShouldGoUpToFarthestLevel() throws Exception {
    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, false);
    buttons.add(2, false);
    buttons.add(3, false);
    buttons.add(4, false);
    buttons.add(5, true);

    int currentLevel = 3;

    int nextLevelToDispatch = 0;
    nextLevelToDispatch = strategy.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(6, nextLevelToDispatch);
  }

  @Test
  void testShouldGoUpToFarthestLevelDespiteHavingCloserLevel() throws Exception {
    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, false);
    buttons.add(2, false);
    buttons.add(3, true);
    buttons.add(4, true);
    buttons.add(5, false);

    int currentLevel = 3;

    int nextLevelToDispatch = 0;
    nextLevelToDispatch = strategy.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(5, nextLevelToDispatch);
  }

  @Test
  void testShouldGoToCloserLevelBelowIfUpAndDownAreOptions() throws Exception {
    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, true);
    buttons.add(2, false);
    buttons.add(3, false);
    buttons.add(4, true);
    buttons.add(5, false);

    int currentLevel = 3;

    int nextLevelToDispatch = 0;
    nextLevelToDispatch = strategy.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(2, nextLevelToDispatch);
  }

  @Test
  void testShouldGoToCloserLevelAboveIfUpAndDownAreOptions() throws Exception {
    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, true);
    buttons.add(2, false);
    buttons.add(3, false);
    buttons.add(4, true);
    buttons.add(5, false);

    int currentLevel = 4;

    int nextLevelToDispatch = 0;
    nextLevelToDispatch = strategy.getNextLevelToDispatch(buttons, currentLevel);

    assertEquals(5, nextLevelToDispatch);
  }

  @Test
  void testThrowNoDispatchExceptionIfNoButtonsPressed() throws Exception {
    List<Boolean> buttons = new ArrayList<>();
    buttons.add(0, false);
    buttons.add(1, false);
    buttons.add(2, false);
    buttons.add(3, false);

    int currentLevel = 2;

    assertThrows(
        NoDispatchException.class, () -> strategy.getNextLevelToDispatch(buttons, currentLevel));
  }
}
