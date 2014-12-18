/*
 * LastFitPackingPolicyTest.groovy
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing

import net.jnellis.binpack.Bin
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: Joe Nellis
 * Date: 12/15/2014 
 * Time: 7:29 AM
 *
 */
class LastFitPackingPolicyTest extends Specification {

  @Shared
  def bins = new ArrayList<Bin<Double>>();

  @Unroll
  def "Pack the last bin with space available."() {
    setup:
    def policy = new LastFitPackingPolicy()
    def availableCapacities = [8d, 4d, 3d].asList()
    expect:
    bins == policy.pack(piece, bins, availableCapacities)
    bins.collect {
      it.getPieces()
    } == result
    where:
    piece || result

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