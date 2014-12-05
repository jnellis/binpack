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
  @Override
  public List<Bin<Double>> pack(Double piece, List<Bin<Double>>
      existingBins, List<Double> availableCapacities) {
    return null;
//    pieces.forEach(piece -> {
//      Bin<Double> lastBin = null;
//      ListIterator<Bin<Double>> iterator = bins.listIterator();
//      if (iterator.hasPrevious()) {
//        lastBin = iterator.previous();
//      }
//      if(lastBin == null || !lastBin.canFit(piece)) {
//        lastBin = createNewBin(bins, capacity);
//      }
//      place(piece,lastBin);
//    });
  }


}
