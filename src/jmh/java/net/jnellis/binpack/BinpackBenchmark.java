/*
 * Copyright (c) 2017.  Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 * BinpackBenchmark.java  lastModified: 10/22/17 9:39 AM
 */

package net.jnellis.binpack;

import net.jnellis.binpack.collectors.BestFitPackingCollector;
import net.jnellis.binpack.packing.*;
import net.jnellis.binpack.preorder.AsIs;
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
  public List<LinearBin> testBestFit() {

    List<LinearBin> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIs<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<LinearBin> testNextFit() {

    List<LinearBin> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIs<>())
        .setPackingPolicy(new NextFit<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);


    return bins;
  }


  @Benchmark
  public List<LinearBin> testAlmostWorstFit() {

    List<LinearBin> bins =
        new LinearBinPacker()
            .setPreOrderPolicy(new AsIs<>())
            .setPackingPolicy(new AlmostWorstFit<>())
            .packAll(
                pieces,
                new ArrayList<>(),
                capacities);


    return bins;
  }

  @Benchmark
  public List<LinearBin> testLastFit() {

    List<LinearBin> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIs<>())
        .setPackingPolicy(new LastFit<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<LinearBin> testFirstFit() {

    List<LinearBin> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIs<>())
        .setPackingPolicy(new FirstFit<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }

  @Benchmark
  public List<LinearBin> testWorstFit() {

    List<LinearBin> bins = new LinearBinPacker()
        .setPreOrderPolicy(new AsIs<>())
        .setPackingPolicy(new WorstFit<>())
        .packAll(
            pieces,
            new ArrayList<>(),
            capacities);

    return bins;
  }
}
