/*
 * BestFitPackingPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;

/**
 * Places a piece in fullest bin that has space.
 *
 * @author Joe Nellis
 */
public class BestFitPackingPolicy implements LinearPackingPolicy {


  @Override
  public List<Bin<Double>> pack(Double piece,
                                List<Bin<Double>> existingBins,
                                List<Double> availableCapacities) {

    Bin<Double> fullestBinThatStillFits = existingBins.stream()
        .filter(bin -> bin.canFit(piece))
        .sorted() // by remaining capacity
        .findFirst()
        .orElseGet(() -> addNewBin(existingBins, piece, availableCapacities));

    fullestBinThatStillFits.add(piece);
    return existingBins;
  }

}

