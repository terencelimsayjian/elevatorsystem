package com.terence.elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LiftDispatcher {

  private List<Boolean> upButtons;
  private List<Boolean> downButtons;
  private List<Lift> lifts;

  public LiftDispatcher(List<Lift> lifts) {
    this.lifts = lifts;
    this.upButtons = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      this.upButtons.add(i, Boolean.FALSE);
      this.downButtons.add(i, Boolean.FALSE);
    }
  }

  public void pressButton(Direction direction, int level) {
    buttonPanel(direction).set(level - 1, true);

    // TODO: More efficiently find idle lift that is closest to the level
    Optional<Lift> optionalLiftToDispatch = lifts.stream().filter(l -> LiftStatus.IDLE == l.getStatus()).findFirst();

    optionalLiftToDispatch.ifPresent(l -> l.dispatchLift(level));
  }

  // onLiftStopAtLevel
  // on


  // On liftstatechange
  // This class needs to keep track of all the lift statuses and the direction they are moving to determine dispatch
  // Does dispatcher need to know everytime the lift level changes?
  //  One model is the lift can tell liftdispatcher when level changes. Lift dispatcher can then tell lift to stop
  //  But this sucks because liftdispatcher will receive every level change for every lift, and it doesn't relaly need to do this (Might need to do this to determine if a dispatch if necessary)
  //  Instead, lift dispatcher wants to know the relevant state changes (IDLE, MOVING)


  public void onLiftIdle() {
    // Find level to dispatch to using strategy
    // Dispatch lift using a strategy

    // Lift dispatcher needs to subscribe to Lift
    // Should we register on the dispatcher, or on the lift? Considering we only have one dispatcher, feels like it should be on the dispatcher
  }

  // TODO: How to unpress buttons outside

  public boolean shouldStop(Direction direction, int level) {
    return buttonPanel(direction).get(level - 1);
  }

  private List<Boolean> buttonPanel(Direction direction) {
    return Direction.UP == direction ? upButtons : downButtons;
  }


}
