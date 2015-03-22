/*
 * PreOrderPolicyTests.groovy
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.preorder

import spock.lang.Specification
import spock.lang.Unroll

/**
 * User: Joe Nellis
 * Date: 3/21/2015 
 * Time: 11:03 AM
 *
 */
class PreOrderPolicyTests extends Specification {
  def list = [6, 3, 5, 1, 2, 4, 8, 9, 7]

  @Unroll
  def "Ordering list with #preOrderPolicy.class.simpleName"() {
    expect:
    assert preOrderPolicy.order(list) == expectedOrdering

    where:
    preOrderPolicy           || expectedOrdering
    new AscendingPolicy<>()  || [1, 2, 3, 4, 5, 6, 7, 8, 9]
    new AsIsPolicy<>()       || [6, 3, 5, 1, 2, 4, 8, 9, 7]
    new DescendingPolicy<>() || [9, 8, 7, 6, 5, 4, 3, 2, 1]
  }

  def "check list with RandomPolicy is not same after ordering."() {
    setup:
    def copyOfList = [] << list
    expect:
    assert new RandomPolicy<>().order(list) != copyOfList

  }

}
