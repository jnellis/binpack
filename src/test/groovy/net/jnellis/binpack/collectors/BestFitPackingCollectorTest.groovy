/*
 * Copyright (c) 2017.  Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 * BestFitPackingCollectorTest.groovy  lastModified: 8/7/17 7:45 PM
 */

package net.jnellis.binpack.collectors

import net.jnellis.binpack.LinearBin
import net.jnellis.binpack.LinearBinPacker
import net.jnellis.binpack.packing.BestFitPackingPolicy
import net.jnellis.binpack.preorder.AsIsPolicy
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Function
import java.util.function.Supplier
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * User: Joe Nellis
 * Date: 8/7/2017 
 * Time: 7:45 PM
 *
 */
class BestFitPackingCollectorTest extends Specification {
  @Shared
  def bins = new ArrayList<LinearBin>();

  def "Create best fit collector from convenience method"() {
    setup:
    final def capacities = [8d, 4d, 3d]

    def collector = BinPackCollector.bestFitPacking(LinearBin.newBinSupplier(capacities),
        Function.identity())
//    def binPacker = new LinearBinPacker().setPackingPolicy(policy)
    def pieces = [8d, 7.89d, 6d, 5d, 3d, 2d, 0.11d]
    def expectedResult = [[8d], [7.89d, 0.11d], [6d, 2d], [5d, 3d]]

    expect:
    pieces.stream().collect(collector).collect {
      it.getPieces()
    }.sort() == expectedResult.sort()

  }

  @Unroll
  def "Pack the fullest bin that still has space."() {
    setup:
    final def availableCapacities = [8d, 4d, 3d]
    Supplier<LinearBin> binSupplier = new Supplier<LinearBin>() {
      @Override
      LinearBin get() {
        return new LinearBin(availableCapacities);
      }
    }

    def collector = new BestFitPackingCollector<>(binSupplier, Function.identity())
//    def binPacker = new LinearBinPacker().setPackingPolicy(policy)
    def pieces = [8d, 7.89d, 6d, 5d, 3d, 2d, 0.11d]
    def expectedResult = [[8d], [7.89d, 0.11d], [6d, 2d], [5d, 3d]]

    expect:

    pieces.stream().collect(collector).collect {
      it.getPieces()
    }.sort() == expectedResult.sort()
  }

  def "Make sure results are the same as binpacker"() {
    final int RANGE = 10000;

    final Double CAPACITY = 8d;

    final int AVG_PIECE = 5;


    final List<Double> pieces =
        Stream.generate({ Math.random() * AVG_PIECE } as Supplier<Double>)
              .limit(RANGE)
              .sorted(Comparator.reverseOrder())
              .collect(Collectors.toList())

    final List<Double> capacities = Collections.singletonList(CAPACITY)

    final Supplier<LinearBin> binSupplier = new Supplier<LinearBin>() {
      @Override
      LinearBin get() {
        return new LinearBin(CAPACITY);
      }
    };


    def collector =
        new BestFitPackingCollector<>(binSupplier, Function.identity())

    def collectorList = new ArrayList<>(pieces.stream().collect(collector))

    def binpackerList = new LinearBinPacker()
        .setPreOrderPolicy(new AsIsPolicy<>())
        .setPackingPolicy(new BestFitPackingPolicy<>())
        .packAll(pieces, new ArrayList<>(), capacities)

    expect:
    collectorList.sort() == binpackerList.sort()


  }

}

