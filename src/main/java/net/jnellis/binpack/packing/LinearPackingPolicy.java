/*
 * LinearPackingPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;
import net.jnellis.binpack.LinearBin;

import java.util.Collections;
import java.util.List;

/**
 * A packing policy for linear sizes. Assumes piece sizes are of type Double.
 *
 * @author Joe Nellis
 */
public interface LinearPackingPolicy extends PackingPolicy<Double> {
  /* todo: can all exceptions be kept here? */

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
  default Bin<Double> addNewBin(List<Bin<Double>> bins,
                                Double piece,
                                List<Double> availableCapacities) {

    assertPieceWouldFitInANewBin(piece, availableCapacities);

    Bin<Double> theBin = new LinearBin(availableCapacities);
    if (!bins.add(theBin)) {
      throw new AssertionError("Can't add bin to list of existingBins.");
    }
    return theBin;
  }

  /**
   * Asserts that if a new bin was created, based on the max capacity of the
   * new bin, the piece would still fit.
   *
   * @param piece      The piece that will be packed later.
   * @param capacities The potential capacities of a new bin.
   */
  default void assertPieceWouldFitInANewBin(Double piece,
                                            List<Double> capacities) {

    if (piece > Collections.max(capacities)) { // Houston!
      // we could check here to see if an existing bin is big enough
      // and then reorder that bin, if possible, and use this piece there.
      throw new AssertionError("Piece is bigger than the maximum " +
          "available capacity of a new bin.");
    }
  }
}
