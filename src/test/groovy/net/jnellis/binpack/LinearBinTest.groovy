/*
 * LinearBinTest.groovy
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack

import spock.lang.Specification

class LinearBinTest extends Specification {
  def "Add pieces to a bin, try to overstuff a couple."() {
    setup:
    // create a bin of 40 capacity only.
    LinearBin bin = new LinearBin([40d].asList())

    expect:
    data.eachWithIndex {it,i->
      assert bin.add(it) == test[i]
    }

    where:
    data << [[100d],
             [10d, 20d, 5d],
             [20d, 20d],
             [5d, 10d, 20d, 5d],
             [10d, 30.1d]]
    test << [[false],
             [true, true, true],
             [true, true],
             [true, true, true, true],
             [true, false]]
  }

  def "Test what can fit in a LinearBin."() {
    setup:
    LinearBin bin = new LinearBin([10d, 20d, 30d, 40d].asList())

    expect:
    assert bin.canFit(data) == test

    where:
    data | test
    40d  | true
    30d  | true
    41d  | false
    5d   | true
    100d | false
  }

  def "Existing bins only have one capacity."(){
    when:
    def bin =   new LinearBin(42.0);

    then:
      assert bin.isExisting() && bin.getCapacities().size() == 1


  }

  def "Compare the max remaining capacity of two bins."(){
    setup:
    LinearBin smallBin = new LinearBin([20d])
    LinearBin bigBin = new LinearBin([40d])

    expect:
    assert smallBin < bigBin
    and:
    bigBin.add(20d);
    assert smallBin.compareTo(bigBin) == 0

    and:
    bigBin.add(1d)
    println "${LinearBin.getMaxRemainingCapacity(smallBin)} -- ${LinearBin.getMaxRemainingCapacity(bigBin)}"
    assert smallBin > bigBin
  }
}
