/*
 * NextFitPackingPolicy.java
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
 * Choose last bin, if the piece fits return the bin otherwise return
 * and empty optional.
 * Different from {@link LastFitPackingPolicy} because it checks if the piece
 * can fit after choosing the last bin. LastFitPackingPolicy filters bins that
 * could fit first then chooses the last bin.
 */
public class NextFitPackingPolicy<T extends Comparable<T>>
    implements PackingPolicy<T> {
  /**
   * Choose last bin, if the piece fits return the bin otherwise return
   * and empty optional.
   * @param piece          The piece to be fitted into an existing bin.
   * @param existingBins   List of existing bins where the piece could fit.
   * @return The Optional bin if it fits or an empty optional.
   */
  public Optional<Bin<T>> chooseBin(T piece, List<Bin<T>> existingBins) {
    return existingBins.stream()
                       .reduce((acc, bin) -> bin)
                       .filter(bin -> bin.canFit(piece));
  }
}
