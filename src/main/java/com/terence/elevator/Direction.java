package com.terence.elevator;

public enum Direction {
  UP(1),
  DOWN(-1);

  final int value;

  Direction(int value) {
    this.value = value;
  }
}
