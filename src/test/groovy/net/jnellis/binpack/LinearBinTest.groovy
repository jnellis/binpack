package net.jnellis.binpack

import spock.lang.Specification

/**
 * User: Joe
 * Date: 12/3/2014 
 * Time: 11:37 AM
 *
 */
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

  def "Compare the max remaining capacity of two bins."(){
    setup:
    LinearBin smallBin = new LinearBin([20d])
    LinearBin bigBin = new LinearBin([40d])

    expect:
    assert smallBin < bigBin

    and:
    bigBin.add(20d);
    assert smallBin < bigBin == false

    and:
    bigBin.add(1d)
    assert smallBin < bigBin == false

  }
}
