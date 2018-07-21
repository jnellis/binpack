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
public class WorstFitPackingPolicy<
    P extends Comparable<P>,
    C extends Comparable<C>>
    implements PackingPolicy<P, C> {

  /**
   * Chooses the emptiest bin first.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return an {@link Optional} that represents the bin it chose, or not.
   */
  @Override
  public Optional<Bin<P, C>> chooseBin(final P piece,
                                       final List<Bin<P, C>> existingBins) {

    return existingBins.stream()
                       .filter(binsThatCanFit(piece))
                       .max(Comparator.naturalOrder());
  }


}
