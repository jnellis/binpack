/*
 * HelloBenchmark.java
 *
 * Copyright (c) 2017. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * User: Joe Nellis
 * Date: 8/4/2017
 * Time: 12:33 PM
 */
@State(Scope.Benchmark)
public class HelloBenchmark {

  final int RANGE = 10000;

  final int MEDIUM = 1500;

  final int SMALL = 500;

  final Double CAPACITY = 8d;

  final int AVG_PIECE = 5;


  List<Double> pieces = DoubleStream.generate(() -> Math.random() * AVG_PIECE)
                                    .limit(RANGE)
                                    .boxed()
                                    .collect(Collectors.toList());

  List<Double> capacities = Collections.singletonList(CAPACITY);


  @Benchmark
  public List<Bin<Double>> testLarge() {
    return new LinearBinPacker().packAll(pieces,
                                         new ArrayList<>(),
                                         capacities);
  }

  @Benchmark
  public List<Bin<Double>> testMedium() {
    return new LinearBinPacker().packAll(pieces.subList(0, MEDIUM),
                                         new ArrayList<>(),
                                         capacities);

  }

  @Benchmark
  public List<Bin<Double>> testSmall() {
    return new LinearBinPacker().packAll(pieces.subList(0, SMALL),
                                         new ArrayList<>(),
                                         capacities);

  }

}
