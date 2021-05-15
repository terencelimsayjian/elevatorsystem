package com.terence.elevator;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;


class LiftDispatcherTest {

  @Test
  void name() throws InterruptedException, ExecutionException {
    BigDecimal bd1 = BigDecimal.valueOf(5, 5);
    BigDecimal bd2 = BigDecimal.valueOf(10.2345);


    BigDecimal result = bd1.add(bd2);

    BigDecimal bd3 = BigDecimal.valueOf(0.1);
    BigDecimal bd4 = BigDecimal.valueOf(0.10);
    System.out.println(bd3.compareTo(bd4));


    assertEquals(0, result.compareTo(BigDecimal.valueOf(15.23450000)));

    BigDecimal divide = bd1.divide(bd2, 6, RoundingMode.UP);

    System.out.println(divide);
  }
}
