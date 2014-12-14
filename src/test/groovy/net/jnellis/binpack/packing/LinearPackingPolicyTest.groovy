/*
 * LinearPackingPolicyTest.groovy
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing

import net.jnellis.binpack.Bin
import spock.lang.Shared
import spock.lang.Specification

/**
 * User: Joe Nellis
 * Date: 12/6/2014
 * Time: 3:15 PM
 *
 */
class LinearPackingPolicyTest extends Specification {

  @Shared
  def policy = new LinearPackingPolicy() {
    @Override
    List<Bin<Double>> pack(Double piece, List<Bin<Double>> existingBins, List<Double> availableCapacities) {
      return null
    }
  }
  @Shared
  def bins = new ArrayList<Bin<Double>>();
  @Shared
  def availableCapacities = [8d, 4d, 3d].asList()
  @Shared
  def piece = 4.1d;

  def "AddNewBin"() {
    when:
    def newBin = policy.addNewBin(bins, piece, availableCapacities)
    then:
    newBin != null
    bins.size() == 1
  }

  def "AddNewBin when piece is larger than max capacity of new bin."() {
    when:
    policy.addNewBin(bins, 9000d, availableCapacities)
    then:
    thrown(AssertionError)

  }
}
