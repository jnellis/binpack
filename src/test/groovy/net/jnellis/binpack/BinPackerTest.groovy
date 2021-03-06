/*
 * BinPackerTest.groovy
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack

import net.jnellis.binpack.packing.BestFit
import net.jnellis.binpack.preorder.Descending
import spock.lang.Specification
/**
 * User: Joe Nellis
 * Date: 3/21/2015 
 * Time: 12:01 PM
 *
 */
class BinPackerTest extends Specification {
  def binPacker = new LinearBinPacker()


  def "PackAll"() {
    def existingBins = []
    def availableCapacities = [96d, 204d]
    def pieces = [76d, 109d, 76d, 80d, 12d, 29d, 12d, 56d, 134d, 87d,
                  134d, 56d, 45d, 42d, 143d, 145d, 183d, 76d, 86d,
                  99d, 34d, 46d, 56d, 18d, 20d]
    def expectedPacking = [[183d, 20d], [145d, 56d], [143d, 56d],
                           [134d, 56d, 12d], [134d, 42d, 12d],
                           [109d, 87d], [99d, 86d, 18d],
                           [80d, 76d, 46d], [76d, 76d, 45d], [34d, 29d]
    ].sort()
    def result = binPacker.packAll(pieces, existingBins, availableCapacities)
                          .collect { it.pieces }.sort()

    expect:
    result == expectedPacking

  }

  def "Pack"() {
    def existingBins = [new LinearBin(5d)] // existing bin with small capacity
    def availableCapacities = [192d] // new bins will be large
    def piece1 = 204d
    def piece2 = 190d

    // assumes exception when piece is bigger than capacity.
    when:
    binPacker.pack(piece1, existingBins, availableCapacities)
    then:
    thrown(AssertionError)



    def result = binPacker.pack(piece2, existingBins, availableCapacities)
    expect:
    result != null
    result.size() == 2
    result[0].getTotal() == 0
    result[1].getTotal() == 190
  }


  def "test BinPacker javadoc example code"() {
    List<Double> pieces = Arrays.asList(3d, 4d, 8d, 5d, 7d) //piece lengths
    List<LinearBin> bins = new ArrayList<>()
    List<Double> capacities = Arrays.asList(6d)// bins are only 6.0 long
    LinearBinPacker binPacker = new SpliceableBinPacker()
    bins = binPacker.setPreOrderPolicy(new Descending<>())
                    .setPackingPolicy(new BestFit<>())
                    .setAvailableCapacitiesPreOrderPolicy(new Descending<>())
                    .packAll(pieces, bins, capacities)

    expect:
    bins*.pieces == [[6.0], [6.0], [5.0, 1.0], [4.0, 2.0], [3.0]]
  }
}
