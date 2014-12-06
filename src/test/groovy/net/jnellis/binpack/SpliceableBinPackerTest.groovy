/*
 * SplicableBinPackerTest.groovy
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack

import net.jnellis.binpack.packing.BestFitPackingPolicy
import net.jnellis.binpack.packing.PackingPolicy
import net.jnellis.binpack.preorder.DescendingPolicy
import net.jnellis.binpack.preorder.PreOrderPolicy
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class SpliceableBinPackerTest extends Specification {
  @Shared
  PackingPolicy<Double> packingPolicy = new BestFitPackingPolicy<>()
  @Shared
  PreOrderPolicy<Double> preOrderPolicy = new DescendingPolicy<>()

  def "SetPackingPolicy returns same BinPacker"() {
    setup:
    SpliceableBinPacker binPacker = new SpliceableBinPacker();
    expect:
    assert binPacker.setPackingPolicy(new BestFitPackingPolicy()).is(binPacker);
  }

  def "Break up pieces that are bigger than maximum available capacity"() {
    setup:
    SpliceableBinPacker binPacker = new SpliceableBinPacker();

    expect:
    assert binPacker.createSplicePieces(pieces, max) == newPieces

    where:
    pieces               | max | newPieces
    [4d, 5d, 6d, 7d, 8d] | 6d  | [4d, 5d, 6d, 6d, 1d, 6d, 2d]
    [7d, 4d, 5d, 6d, 2d] | 8d  | [7d, 4d, 5d, 6d, 2d]
    [5d, 6d, 7d]         | 3d  | [3d, 2d, 3d, 3d, 3d, 3d, 1d]
    [0d, 0d, 0d]         | 4d  | []
    [0d, 9d, 0d]         | 4d  | [4d, 4d, 1d]
  }

  @Ignore
  @Unroll
  def "PackAll with #pop pieces into #pp with #existingBins existing bins and capacity: #capacities"() {
    setup:
    SpliceableBinPacker binPacker = new SpliceableBinPacker()

    binPacker.setPackingPolicy(packingPolicy).setPreOrderPolicy(preOrderPolicy)

    expect:
    bins.each{ it.existing = true }
    assert binPacker.packAll(pieces, bins, capacities) == newBins

    where:
    pieces = [1d, 2d, 3d, 3d, 4d, 5d, 6d, 8d]

    // adding true sets a flag that it is an existing bin.
    bins = [new LinearBin([5d],true), new LinearBin([4d],true)]

    capacities = [6d, 3d]

    // new pieces are 6,6,5,4,3,3,2,2,1
    newBins =[[5d],[4d],[6d],[6d],[3d,3d],[2d,2d,1d]]

    pp = this.packingPolicy.getClass().getSimpleName()
    pop = this.preOrderPolicy.getClass().getSimpleName()
    existingBins = bins.size();
  }

}
