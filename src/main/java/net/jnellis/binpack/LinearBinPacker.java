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
public class LinearBinPacker extends BinPacker<Double> {

  /**
   * Creates a new <code>LinearBin</code> using <code>availableCapacities</code>
   * and then adds it to the list of <code>bins</code>.
   *
   * @param bins                List of bins.
   * @param piece               A piece used to assert it would fit a max
   *                            capacity bin.
   * @param availableCapacities List of capacities that the new bin could be.
   * @return the new LinearBin.
   */
  Bin<Double> addNewBin(List<Bin<Double>> bins,
                        Double piece,
                        List<Double> availableCapacities) {

    assertPieceWouldFitInANewBin(piece, availableCapacities);

    Bin<Double> theBin = new LinearBin(availableCapacities);
    if (!bins.add(theBin)) {
      throw new AssertionError("Can't add bin to list of existingBins.");
    }
    return theBin;
  }

}
