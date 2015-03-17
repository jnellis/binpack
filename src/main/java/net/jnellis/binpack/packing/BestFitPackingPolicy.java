/*
 * BestFitPackingPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;
import java.util.Optional;

/**
 * Choose the fullest bin that has space.
 */
public class BestFitPackingPolicy<T extends Comparable<T>>
    implements PackingPolicy<T> {

  /**
   * Choose the fullest bin with space available.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return An {@link Optional } that represents the bin it found.
   */
  public Optional<Bin<T>> chooseBin(T piece, List<Bin<T>> existingBins) {

    // bins are compared by remaining capacity
    return existingBins.stream()
                       .filter(bin -> bin.canFit(piece))
                       .sorted()
                       .findFirst();
  }
}

