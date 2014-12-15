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

/**
 * Places pieces in the closest bin first.
 */
public class FirstFitPackingPolicy implements LinearPackingPolicy {

  /**
   * Attempts to place <code>pieces</code> into bins of <code>existing</code>
   * sizes first then uses <code>availableCapacities</code> bin sizes when it
   * needs to
   * fill a new bin.
   *
   * @param piece               The pieces that need to be packed.
   * @param existingBins        Existing bins that pieces will be packed
   *                            into first.
   * @param availableCapacities Available bin sizes that we can create.
   * @return Returns a stream of packed bins.
   */
  @Override
  public List<Bin<Double>> pack(Double piece,
                                List<Bin<Double>> existingBins,
                                List<Double> availableCapacities) {

    Bin<Double> firstBinThatFits = existingBins.stream()
        .filter(bin -> bin.canFit(piece))
        .findFirst()
        .orElseGet(() -> addNewBin(existingBins, piece, availableCapacities));

    firstBinThatFits.add(piece);
    return existingBins;
  }


}
