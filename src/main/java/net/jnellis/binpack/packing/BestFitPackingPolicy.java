/*
 * BestFitPackingPolicy.java
 *
 * Created on August 19, 2006, 6:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;

/**
 *  Places a piece in fullest bin that has space.
 * @author Joe Nellis
 */
public class BestFitPackingPolicy implements LinearPackingPolicy {


  @Override
  public List<Bin<Double>> pack(Double piece, List<Bin<Double>>
      existingBins, List<Double> availableCapacities) {
    return null;


//
//    pieces.forEach(piece->{
//       LinearBin fullestFittableBin = bins.stream().filter(bin-> bin.canFit(piece))
//          .sorted()
//          .findFirst()
//          .orElse(createNewBin(bins,capacity));
//      place(piece,fullestFittableBin);
//    });
  }
}
