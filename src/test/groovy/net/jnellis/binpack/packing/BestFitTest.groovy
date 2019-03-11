/*
 * BestFitTest.groovy
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
 * Date: 12/6/2014 
 * Time: 8:21 PM
 *
 */
class BestFitTest extends Specification {

  @Shared
  def bins = new ArrayList<Bin<Double>>();

  @Unroll
  def "Pack the fullest bin that still has space."() {
    setup:
    def policy = new BestFit()
    def binPacker = new LinearBinPacker().setPackingPolicy(policy)
    def availableCapacities = [8d, 4d, 3d].asList()
    expect:
    binPacker.pack(piece, bins, availableCapacities).collect {
      it.getPieces()
    } == expectedResult
    where:
    piece || expectedResult

    8d    || [[8d]]
    7.9d  || [[8d], [7.9d]]
    6d    || [[8d], [7.9d], [6d]]
    5d    || [[8d], [7.9d], [6d], [5d]]
    3d    || [[8d], [7.9d], [6d], [5d, 3d]]
    2d    || [[8d], [7.9d], [6d, 2d], [5d, 3d]]
  }
}
