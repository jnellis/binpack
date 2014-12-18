/*
 * NextFitPackingPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

/*
 * NextFitPackingPolicy.java
 *
 * Created on September 3, 2006, 4:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;

/**
 * Places pieces in last bin or a new bin.
 * @author Joe Nellis
 */
public class NextFitPackingPolicy implements LinearPackingPolicy {
  /**
   * Attempts to place one <code>piece</code> into bins of
   * <code>existingBins</code>
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
  public List<Bin<Double>> pack(Double piece, List<Bin<Double>> existingBins,
                                List<Double> availableCapacities) {

    Bin<Double> lastBin = existingBins.stream()
        .max((a, b) -> -1) // aka findLast
        .filter(bin -> bin.canFit(piece))
        .orElseGet(() -> addNewBin(existingBins, piece, availableCapacities));

    lastBin.add(piece);
    return existingBins;
  }

}
