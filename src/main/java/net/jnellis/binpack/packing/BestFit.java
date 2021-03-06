/*
 * BestFit.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Choose the fullest bin that has space.
 */
public class BestFit<
    P extends Comparable<P>,
    C extends Comparable<C>,
    B extends Bin<P,C>>
    implements PackingPolicy<P, C, B> {

  /**
   * Choose the fullest bin with space available.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return An {@link Optional } that represents the bin it found.
   */
  @Override
  public Optional<B> chooseBin(final P piece, final List<B> existingBins) {

    // bins are compared by remaining capacity
    return existingBins.stream()
                       .filter(binsThatCanFit(piece))
                       .min(Comparator.naturalOrder());
  }

}

