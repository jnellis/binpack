/*
 * AlmostWorstFitPackingPolicy.java
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
public class AlmostWorstFitPackingPolicy<T extends Comparable<T>>
    implements PackingPolicy<T> {

  /**
   * Chooses the second emptiest bin. Returns an {@link Optional }
   * which could be empty if no suitable bin was found.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return An {@link Optional } that represents the bin it found.
   */
  @SuppressWarnings("unchecked")
  public Optional<Bin<T>> chooseBin(final T piece,
                                    final List<Bin<T>> existingBins) {

    if (existingBins.size() < 2) {
      return Optional.empty();
    }

    final Bin<T> firstBin = existingBins.get(0);
    final Bin<T>[] min2 = (Bin<T>[]) Array.newInstance(firstBin.getClass(), 2);

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
  private void updateMin2(final Bin<T>[] min2, final Bin<T> bin) {

    if (min2[0] == null || bin.compareTo(min2[0]) > 0) {
      min2[1] = min2[0];
      min2[0] = bin;
    } else if (min2[1] == null || bin.compareTo(min2[1]) > 0) {
      min2[1] = bin;
    }
  }

}