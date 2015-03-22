/*
 * LinearBinPackerTest.groovy
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack

import spock.lang.Shared
import spock.lang.Specification

/**
 * User: Joe Nellis
 * Date: 3/13/2015 
 * Time: 12:56 PM
 *
 */
class LinearBinPackerTest extends Specification {
  @Shared
  def binPacker = new LinearBinPacker()
  @Shared
  def bins = new ArrayList<Bin<Double>>()
  @Shared
  def availableCapacities = [8d, 4d, 3d].asList()
  @Shared
  def piece = 4.1d;

  def "AddNewBin"() {
    when:
    def newBin = binPacker.addNewBin(bins, piece, availableCapacities)
    then:
    newBin != null
    bins.size() == 1
  }

  def "AddNewBin when piece is larger than max capacity of new bin."() {
    when:
    binPacker.addNewBin(bins, 9000d, availableCapacities)
    then:
    thrown(AssertionError)

  }
}
