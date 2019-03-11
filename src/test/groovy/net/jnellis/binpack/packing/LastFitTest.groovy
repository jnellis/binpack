/*
 * LastFitTest.groovy
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
 * Date: 12/15/2014 
 * Time: 7:29 AM
 *
 */
class LastFitTest extends Specification {

  @Shared
  def bins = new ArrayList<Bin<Double>>();

  @Unroll
  def "Pack the last bin with space available."() {
    setup:
    def policy = new LastFit()
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
    3.6d  || [[8d], [5d], [5d], [4d, 3.6d]]
    2.5d  || [[8d], [5d], [5d, 2.5d], [4d, 3.6d]]
    2.3d  || [[8d], [5d, 2.3d], [5d, 2.5d], [4d, 3.6d]]
    0.5d  || [[8d], [5d, 2.3d], [5d, 2.5d, 0.5d], [4d, 3.6d]]
  }
}
