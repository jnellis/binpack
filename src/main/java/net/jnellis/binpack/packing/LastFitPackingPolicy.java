/*
 * LastFitPackingPolicy.java
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
 * Chooses the farthest bin first.
 * Differs from {@link NextFitPackingPolicy} in that it filters pieces that
 * could fit and then picks the last of them or returns an empty Optional.
 */
public class LastFitPackingPolicy<T extends Comparable<T>>
    implements PackingPolicy<T> {


  /**
   * Of all the pieces that fit, return the last one or an empty Optional.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return an Optional Bin that represents the bin it found, or not.
   */
  public Optional<Bin<T>> chooseBin(final T piece,
                                    final List<Bin<T>> existingBins) {

    return PackingPolicy.reverseStream(existingBins)
                        .filter(binsThatCanFit(piece))
                        .findFirst();

  }
}
