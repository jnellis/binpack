/*
 * WorstFitPackingPolicy.java
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Chooses the emptiest bin first.
 */
public class WorstFitPackingPolicy<T extends Comparable<T>>
    implements PackingPolicy<T> {

  /**
   * Chooses the emptiest bin first.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return an {@link Optional} that represents the bin it chose, or not.
   */
  public Optional<Bin<T>> chooseBin(T piece, List<Bin<T>> existingBins) {
    return existingBins.stream()
        .filter(bin -> bin.canFit(piece))
        .sorted(Comparator.reverseOrder())
        .findFirst();
  }


}
