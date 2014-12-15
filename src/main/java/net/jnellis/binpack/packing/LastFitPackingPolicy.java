/*
 * LastFitPackingPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

/*
 * LastFitPackingPolicy.java
 *
 * Created on September 3, 2006, 5:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;

/**
 * Places pieces in the farthest bin first.
 *
 * @author Joe Nellis
 */
public class LastFitPackingPolicy implements LinearPackingPolicy {

  /**
   * Attempts to place <code>piece</code> into the last bin of
   * <code>existingBins</code> first then uses
   * <code>availableCapacities</code> bin sizes when it
   * needs to fill a new bin.
   *
   * @param piece               The pieces that need to be packed.
   * @param existingBins        Existing bins that pieces will be packed
   *                            into first.
   * @param availableCapacities Available bin sizes that we can create.
   * @return The collection <code>existingBins</code>
   */
  @Override
  public List<Bin<Double>> pack(Double piece, List<Bin<Double>>
      existingBins, List<Double> availableCapacities) {

    Bin<Double> lastBinThatFits = existingBins.stream()
        .filter(bin -> bin.canFit(piece))
        .max((a, b) -> -1) // aka findLast
        .orElseGet(() -> addNewBin(existingBins, piece, availableCapacities));

    lastBinThatFits.add(piece);
    return existingBins;
  }
}
