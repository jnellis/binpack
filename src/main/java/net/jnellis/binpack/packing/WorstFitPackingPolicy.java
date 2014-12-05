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

import java.util.List;

/**
 * Places pieces into the emptiest bin first.
 *
 * @author Joe Nellis
 */
public class WorstFitPackingPolicy implements LinearPackingPolicy {
  @Override
  public List<Bin<Double>> pack(Double piece, List<Bin<Double>>
      existingBins, List<Double> availableCapacities) {
    return null;

//    pieces.forEach(piece -> {
//      LinearBin<Double> emptiest = new BinImpl<Double>(capacity);
//      // find the emptiest bin or create a new one.
//      emptiest = bins.stream().min(emptiest::compare)
//          .orElse(createNewBin(bins, capacity));
//
//      assert emptiest.canFit(piece);
//      place(piece, emptiest);
//    });
  }


}
