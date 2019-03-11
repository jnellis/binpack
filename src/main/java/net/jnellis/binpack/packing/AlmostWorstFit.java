/*
 * AlmostWorstFit.java
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

/**
 * Chooses the second emptiest bin.
 */
public class AlmostWorstFit<
    P extends Comparable<P>,
    C extends Comparable<C>,
    B extends Bin<P, C>>
    implements PackingPolicy<P, C, B> {

  /**
   * Chooses the second emptiest bin. Returns an {@link Optional }
   * which could be empty if no suitable bin was found.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return An {@link Optional } that represents the bin it found.
   */
  @SuppressWarnings("unchecked")
  @Override
  public Optional<B> chooseBin(final P piece, final List<B> existingBins) {

    if (existingBins.size() < 2) {
      return Optional.empty();
    }

    final B firstBin = existingBins.get(0);
    final B[] min2 =(B[]) Array.newInstance(firstBin.getClass(), 2);

    existingBins.stream()
                .filter(binsThatCanFit(piece))
                .forEach((bin) -> updateMin2(min2, bin));

    return Optional.ofNullable(min2[1]);
  }

  /**
   * Moves {@code bin} into {@code min2} if it has more space available than
   * either bin in {@code min2}.
   *
   * @param min2 ongoing result container for two bins
   * @param bin  a potential emptier bin than either in {@code min2}
   */
  private void updateMin2(final B[] min2, final B bin) {

    if (min2[0] == null || bin.compareTo(min2[0]) > 0) {
      min2[1] = min2[0];
      min2[0] = bin;
    } else if (min2[1] == null || bin.compareTo(min2[1]) > 0) {
      min2[1] = bin;
    }
  }


}