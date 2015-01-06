/*
 * WorstFitPackingPolicy.java
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

/*
 * WorstFitPackingPolicy.java
 *
 * Created on September 3, 2006, 5:34 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;
import net.jnellis.binpack.LinearBin;

import java.util.List;

import static java.util.Comparator.comparing;

/**
 * Places pieces into the emptiest bin first.
 *
 * @author Joe Nellis
 */
public class WorstFitPackingPolicy implements LinearPackingPolicy {
  @Override
  public List<Bin<Double>> pack(Double piece, List<Bin<Double>>
      existingBins, List<Double> availableCapacities) {

    Bin<Double> emptiestBin = existingBins.stream()
        .filter(bin -> bin.canFit(piece))
        .max(comparing(LinearBin::getMaxRemainingCapacity))
        .orElseGet(() -> addNewBin(existingBins, piece, availableCapacities));

    emptiestBin.add(piece);
    return existingBins;
  }


}
