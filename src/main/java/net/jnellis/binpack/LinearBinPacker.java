/*
 * LinearBinPacker.java
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import java.util.List;

/**
 * A bin packer for length types only.
 */
public class LinearBinPacker extends BinPacker<Double, Double> {

  /**
   * Creates a new {@code LinearBin} using {@code availableCapacities}
   * and then adds it to the list of {@code bins}.
   *
   * @param piece               A piece used to assert it would fit a max
   *                            capacity bin.
   * @param bins                List of bins.
   * @param availableCapacities List of capacities that the new bin could be.
   * @return the new LinearBin.
   */
  @Override
  Bin<Double, Double> addNewBin(final Double piece,
                                final List<Bin<Double, Double>> bins,
                                final List<Double> availableCapacities) {

    final Bin<Double, Double> theBin = new LinearBin(availableCapacities);
    if (!theBin.canFit(piece) || !bins.add(theBin)) {
      throw new AssertionError("Can't add bin to list of existingBins.");
    }
    return theBin;
  }

}
