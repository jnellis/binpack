/*
 * HelloBenchmark.java
 *
 * Copyright (c) 2017. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import net.jnellis.binpack.packing.*;
import net.jnellis.binpack.preorder.AsIsPolicy;
import net.jnellis.binpack.preorder.DescendingPolicy;
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

  final int AVG_PIECE = 5;  // move this closer to capacity to create more bins


  List<Double> pieces = DoubleStream.generate(() -> Math.random() * AVG_PIECE)
                                    .limit(RANGE)
                                    .boxed()
                                    .collect(Collectors.toList());

  List<Double> capacities = Collections.singletonList(CAPACITY);


  @Benchmark
  public List<Bin<Double>> testLargeBestFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double>> testLargeNextFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new NextFitPackingPolicy<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double>> testMediumBestFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .packAll(
            pieces.subList(0, MEDIUM),
            new ArrayList<>(),
            capacities);

    return bins;

  }

  @Benchmark
  public List<Bin<Double>> testSmallBestFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .packAll(
            pieces.subList(0, SMALL),
            new ArrayList<>(),
            capacities);

    return bins;

  }

  @Benchmark
  public List<Bin<Double>> testSmallBestFitDecreasing() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new DescendingPolicy<>())
        .packAll(
            pieces.subList(0, SMALL),
            new ArrayList<>(),
            capacities);

    return bins;

  }

  @Benchmark
  public List<Bin<Double>> testLargeAlmostWorstFit() {

    List<Bin<Double>> bins =
        new LinearBinPacker()
            .setPreOrderPolicy(new AsIsPolicy<>())
            .setPackingPolicy(new AlmostWorstFitPackingPolicy<>())
            .packAll(
                pieces,
                new ArrayList<>(),
                capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double>> testSmallAlmostWorstFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new AlmostWorstFitPackingPolicy<>())
        .packAll(
            pieces.subList(0, SMALL),
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double>> testSmallNextFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new NextFitPackingPolicy<>())
        .packAll(
            pieces.subList(0, SMALL),
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double>> testSmallLastFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new LastFitPackingPolicy<>())
        .packAll(
            pieces.subList(0, SMALL),
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double>> testSmallFirstFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new FirstFitPackingPolicy<>())
        .packAll(
            pieces.subList(0, SMALL),
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double>> testSmallWorstFit() {

    List<Bin<Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new WorstFitPackingPolicy<>())
        .packAll(
            pieces.subList(0, SMALL),
            new ArrayList<>(),
            capacities);

    return bins;
  }
}
