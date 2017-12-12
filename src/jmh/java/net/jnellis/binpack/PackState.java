/*
 * HelloBenchmark.java
 *
 * Copyright (c) 2017. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User: Joe Nellis
 * Date: 8/4/2017
 * Time: 12:33 PM
 */
@State(Scope.Benchmark)
public class PackState {

  private final Random random = new Random(42);

  public int BINS = 100;

  public Double CAPACITY = 100d;

  public List<Double> pieces;

  public List<Double> capacities;

  // move this closer to capacity to create more bins
  @Param({"100", "80", "60", "40", "20", "5"})
  int maxPieceSize;

  // create optimal bins
  @Setup
  public void setup() {

    pieces =
        IntStream.range(0, BINS)
                 .mapToObj((i) -> createOptimalBin(maxPieceSize, CAPACITY))
                 .map(Bin::getPieces)
                 .flatMap(Collection::stream)
                 .collect(Collectors.toList());

    capacities = Collections.singletonList(CAPACITY);


    Collections.shuffle(pieces, random);

    System.out.println("Working with " + pieces.size() + " pieces that " +
                           "optimally fit into " + BINS + " bins of " +
                           "size " + CAPACITY);
  }

  private Bin<Double, Double> createOptimalBin(final int maxPieceSize,
                                               final double capacity) {

    final DoubleSupplier randomPiece = () -> random.nextDouble() * maxPieceSize;

    final Bin<Double, Double> pieces = new LinearBin(capacity);
    double piece = randomPiece.getAsDouble();
    while (pieces.canFit(piece)) {
      pieces.add(piece);
      piece = randomPiece.getAsDouble();
    }
    pieces.add(pieces.getMaxRemainingCapacity());
    return pieces;
  }
}
