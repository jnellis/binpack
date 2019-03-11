/*
 * Copyright (c) 2017.  Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 * CapacitySupport.java  lastModified: 8/25/17 9:28 AM
 */

package net.jnellis.binpack;

import java.util.List;

/**
 * Allows the concept of a capacity or to enable aggregate operations of the set
 * of pieces in a bin.  We can order unknown 'piece' types that are comparable
 * to each other but not account (add,count) for them in whichever way defines a
 * 'filled' bin. If and however a bin can do these things this interface intends
 * to bridge this abstraction.
 */
public interface CapacitySupport<C extends Comparable<C>> {

  /**
   * Convenience exception Supplier for dealing with the case where a bin has no
   * capacities to base calculations on. This method ideally should never be
   * called.
   *
   * @return An IllegalStateException informing that there must be at least one
   * capacity value from {@link #getCapacities()}
   */
  static IllegalStateException mustBeAtLeastOneCapacityException() {

    return new IllegalStateException("There must be at least one capacity.");
  }

  /**
   * Returns the representation of the aggregate of items in the bin.
   *
   * @return a total within the context of the type of bin.
   */
  C getTotal();

  /**
   * A list of potential capacities that this bin could have.
   *
   * @return a list of capacities.
   */
  List<C> getCapacities();

  /**
   * Computes the remaining capacity of this bin based on the maximum of its
   * potential capacities.
   *
   * @return The maximum potential remaining capacity.
   */
  C getMaxRemainingCapacity();

  /**
   * Finds the minimal capacity needed given the current total.
   *
   * @return The minimal capacity of this bins capacities that is still bigger
   * than the total packed.
   */
  C getSmallestCapacityNeeded();

}
