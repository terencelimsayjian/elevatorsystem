package com.terence.elevator;

public class ElevatorSystemApplication {

  public static void main (String[] args) {
    System.out.println("Welcome to Elevator System");

    // Two kinds of user interaction
    // Press button from outside (On the floor)
    // Press button from inside (Within the lift)

    // Single lift system

    // Build basic lift system, single lift with 5 levels (1-5)

    /*
    LiftDispatcher
    - When button is pressed, dispatch IDLE lift
    - Know's which buttons are pressed and can tell a lift if it needs to stop at a level
    - Lifts notify this when IDLE, and next dispatch command is sent to that lift


    Lift
    - Two states: MOVING (Toward target) / IDLE

    - Need to keep track of destination it's dispatched to
    - LIft know which floor it's on, checks with LiftDispatcher if door needs to be opened


    - Notifies LiftDispatcher when it's IDLE

     */
  }
}
