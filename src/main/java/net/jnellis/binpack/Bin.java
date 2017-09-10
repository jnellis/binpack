/*
 * Bin.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import java.util.List;

/**
 * A packing bin used by {@link BinPacker}. A bin has a capacity, can contain
 * pieces, has a means to determine if a piece can fit in the bin and finally
 * if the bin was 'existing.' An existing bin means it was not created during
 * the packing operation but exists as a given bin with the intention that it
 * represents results from a previous or partial packing operation.
 */
public interface Bin<T extends Comparable<T>> extends Comparable<Bin<T>> {

  /**
   * Convenience exception Supplier for dealing with the case where a bin
   * has no capacities to base calculations on.
   * This method ideally should never be called.
   *
   * @return An IllegalStateException informing that there must be at least
   * one capacity value from {@link #getCapacities()}
   */
  static IllegalStateException mustBeAtLeastOneCapacityException() {

    return new IllegalStateException("There must be at least one capacity.");
  }

  /**
   * Add a piece to this bin. The piece should be checked by calling
   * {@code canFit()} first.
   *
   * @param piece The piece to add.
   * @return true if the piece was added, false if it would not fit.
   */
  boolean add(T piece);


  /**
   * Computes the remaining capacity of this bin based on the maximum
   * of its potential capacities.
   *
   * @return The maximum potential remaining capacity.
   */
  T getMaxRemainingCapacity();

  @Override
  default int compareTo(final Bin<T> o) {
    return getMaxRemainingCapacity().compareTo(o.getMaxRemainingCapacity());
  }

  /**
   * Determines if the offered piece will fit in the bin.
   *
   * @param piece The piece to fit.
   * @return true if the piece will fit.
   */
  boolean canFit(T piece);

  /**
   * Returns whether this bin is an existing bin and not created on the fly
   * during the previous packing operation.
   *
   * @return true if this is an existing bin.
   */
  boolean isExisting();

  /**
   * Returns the representation of the aggregate of items in the bin.
   *
   * @return a total within the context of the type of bin.
   */
  T getTotal();

  /**
   * A list of potential capacities that this bin could have.
   *
   * @return a list of current capacities that this bin could have.
   */
  List<T> getCapacities();

  /**
   * The pieces contained in the bin.
   *
   * @return a list of current pieces in this bin.
   */
  List<T> getPieces();


  /**
   * Finds the minimal capacity needed given the current total.
   *
   * @return The minimal capacity of this bins capacities that is still
   * bigger than the total packed.
   */
  T getSmallestCapacityNeeded();
}
