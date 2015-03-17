/*
 * FirstFitPackingPolicy.java
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
 * Choose the first bin that will fit.
 */
public class FirstFitPackingPolicy<T extends Comparable<T>>
    implements PackingPolicy<T> {

  /**
   * Choose first bin that will fit.
   *
   * @param piece             The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return Returns an Optional bin that represents the bin it found, or not.
   */
  public Optional<Bin<T>> chooseBin(T piece, List<Bin<T>> existingBins) {
    return existingBins.stream()
                       .filter(bin -> bin.canFit(piece))
                       .findFirst();
  }


}
