/*
 * AlmostWorstFitPackingPolicyTest.groovy
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing

import groovy.util.logging.Log
import net.jnellis.binpack.Bin
import net.jnellis.binpack.LinearBinPacker
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: Joe Nellis
 * Date: 3/10/2015 
 * Time: 9:46 PM
 *
 */
@Log
class AlmostWorstFitPackingPolicyTest extends Specification {
  @Shared
  def bins = new ArrayList<Bin<Double>>();

  @Unroll
  def "Pack the second emptiest bin or a new bin."() {
    setup:
    def policy = new AlmostWorstFitPackingPolicy()
    def binPacker = new LinearBinPacker().setPackingPolicy(policy)
    def availableCapacities = [8d].asList()
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
    3.6d  || [[8d], [5d], [5d], [4d], [3.6d]]
    2.5d  || [[8d], [5d], [5d], [4d, 2.5d], [3.6d]]
    2.3d  || [[8d], [5d, 2.3d], [5d], [4d, 2.5d], [3.6d]]
    0.5d  || [[8d], [5d, 2.3d], [5d, 0.5d], [4d, 2.5d], [3.6d]]
  }
}
