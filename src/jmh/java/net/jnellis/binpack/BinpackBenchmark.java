/*
 * Copyright (c) 2017.  Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 * BinpackBenchmark.java  lastModified: 10/22/17 9:39 AM
 */

package net.jnellis.binpack;

import net.jnellis.binpack.collectors.BestFitPackingCollector;
import net.jnellis.binpack.packing.*;
import net.jnellis.binpack.preorder.AsIsPolicy;
import org.openjdk.jmh.annotations.Benchmark;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * User: Joe Nellis
 * Date: 10/22/2017
 * Time: 9:39 AM
 */
public class BinpackBenchmark extends PackState {


  @Benchmark
  public Collection<LinearBin> testBestFitCollector() {

    Supplier<LinearBin> addNewBin = () -> new LinearBin(capacities);
    Collection<LinearBin> bins =
        pieces.stream()
              .collect(new BestFitPackingCollector<>(
                  addNewBin,
                  Function.identity()));

    return bins;
  }


  @Benchmark
  public List<Bin<Double, Double>> testBestFit() {

    List<Bin<Double, Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double, Double>> testNextFit() {

    List<Bin<Double, Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new NextFitPackingPolicy<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);


    return bins;
  }


  @Benchmark
  public List<Bin<Double, Double>> testAlmostWorstFit() {

    List<Bin<Double, Double>> bins =
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
  public List<Bin<Double, Double>> testLastFit() {

    List<Bin<Double, Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new LastFitPackingPolicy<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double, Double>> testFirstFit() {

    List<Bin<Double, Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new FirstFitPackingPolicy<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<Bin<Double, Double>> testWorstFit() {

    List<Bin<Double, Double>> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new WorstFitPackingPolicy<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }
}
