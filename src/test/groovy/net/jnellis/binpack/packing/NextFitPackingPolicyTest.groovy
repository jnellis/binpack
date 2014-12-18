/*
 * NextFitPackingPolicyTest.groovy
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
 * Date: 12/17/2014 
 * Time: 12:29 AM
 *
 */
class NextFitPackingPolicyTest extends Specification {
  @Shared
  def bins = new ArrayList<Bin<Double>>();

  @Unroll
  def "Pack the last bin or a new bin."() {
    setup:
    def policy = new NextFitPackingPolicy()
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
    2.5d  || [[8d], [5d], [5d], [4d, 3.6d], [2.5d]]
    2.3d  || [[8d], [5d], [5d], [4d, 3.6d], [2.5d, 2.3d]]
    0.5d  || [[8d], [5d], [5d], [4d, 3.6d], [2.5d, 2.3d, 0.5d]]
  }
}
