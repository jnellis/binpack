/*
 * FirstFitPackingPolicyTest.groovy
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing

import net.jnellis.binpack.Bin
import net.jnellis.binpack.LinearBinPacker
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: Joe Nellis
 * Date: 12/14/2014 
 * Time: 12:56 PM
 *
 */
class FirstFitPackingPolicyTest extends Specification {

  @Shared
  def bins = new ArrayList<Bin<Double>>();

  @Unroll
  def "Pack the first bin with space available."() {
    setup:
    def policy = new FirstFitPackingPolicy()
    def binPacker = new LinearBinPacker().setPackingPolicy(policy)
    def availableCapacities = [8d, 4d, 3d].asList()
    expect:
    binPacker.pack(piece, bins, availableCapacities).collect {
      it.getPieces()
    } == expectedResult
    where:
    piece || expectedResult

    8d    || [[8d]]
    5d    || [[8d], [5d]]
    5d    || [[8d], [5d], [5d]]
    4d    || [[8d], [5d], [5d], [4d]]
    3d    || [[8d], [5d, 3d], [5d], [4d]]
    2d    || [[8d], [5d, 3d], [5d, 2d], [4d]]
  }
}
