/*
 * AlmostWorstFitPackingPolicy.java
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
 * Chooses the second emptiest bin.
 */
public class AlmostWorstFitPackingPolicy<T extends Comparable<T>>
    implements PackingPolicy<T> {
  /**
   *  Chooses the second emptiest bin. Returns an {@link Optional }
   *  which could be empty if no suitable bin was found.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return An {@link Optional } that represents the bin it found.
   */
  public Optional<Bin<T>> chooseBin(T piece, List<Bin<T>> existingBins) {
    return existingBins.stream()
                       .filter(bin -> bin.canFit(piece))
                       .sorted(Comparator.reverseOrder())
                       .skip(1)
                       .findFirst();
  }
}