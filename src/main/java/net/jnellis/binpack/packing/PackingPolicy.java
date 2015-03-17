/*
 * PackingPolicy.java
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;
import java.util.Optional;

/**
 * A packing algorithm that attempts to find a bin that can fit a piece from a
 * list of bins.
 */
@FunctionalInterface
public interface PackingPolicy<T extends Comparable<T>> {


  /**
   * Chooses a bin that will fit the piece from a list of bins.
   *
   * @param piece             The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return Returns an Optional bin that represents the bin it found, or not.
   */
  Optional<Bin<T>> chooseBin(T piece, List<Bin<T>> existingBins);

}
