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

import java.util.stream.Stream;

/**
 * Places pieces into the emptiest bin first.
 *
 * @author Joe Nellis
 */
public class WorstFitPackingPolicy implements LinearPackingPolicy {
  @Override
  public Stream<Bin<Double>> pack(Stream<Double> pieces, Stream<Bin<Double>>
      existingBins, Stream<Double> availableCapacities) {
    return null;

//    pieces.forEach(piece -> {
//      LinearBin<T> emptiest = new BinImpl<T>(capacity);
//      // find the emptiest bin or create a new one.
//      emptiest = bins.stream().min(emptiest::compare)
//          .orElse(createNewBin(bins, capacity));
//
//      assert emptiest.canFit(piece);
//      place(piece, emptiest);
//    });
  }


}
