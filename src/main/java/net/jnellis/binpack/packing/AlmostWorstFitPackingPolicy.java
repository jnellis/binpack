/*
 * AlmostWorstFitPackingPolicy.java
 *
 * Created on September 4, 2006, 1:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.stream.Stream;

/**
 * Places pieces in the second emptiest bin first.
 *
 * @author Joe Nellis
 */
public class AlmostWorstFitPackingPolicy implements LinearPackingPolicy {
  /**
   * Attempts to place <code>pieces</code> into bins of <code>existing</code>
   * sizes first then uses <code>available</code> bin sizes when it needs to
   * fill a new bin.
   *
   * @param pieces              The pieces that need to be packed.
   * @param existingBins        Existing bins that pieces will be packed
   *                            into first.
   * @param availableCapacities Available bin sizes that we can create.
   * @return Returns a stream of packed bins.
   */
  @Override
  public Stream<Bin<Double>> pack(Stream<Double> pieces, Stream<Bin<Double>>
      existingBins, Stream<Double> availableCapacities) {
    return null;


//
//    pieces.stream().forEach((Double piece) -> {
//      // create two completely full bins
//      LinearBin emptiest = new LinearBin(available);
//      emptiest.add(capacity);
//      LinearBin emptiest2 = new LinearBin<>(capacity);
//      emptiest.add(capacity);
//
//      for (LinearBin<Double> bin : bins) {
//        if (bin.compareDoubleo(emptiest2) < 0) {
//          emptiest2 = bin;
//          if (emptiest2.compareTo(emptiest) < 0) {
//            LinearBin<Double> temp = emptiest;
//            emptiest = emptiest2;
//            emptiest2 = temp;
//          }
//        }
//      }
//      // if second emptiest still doesn't have room create a new Bin.
//      if (!emptiest2.canFit(piece)) {
//        emptiest2 = createNewBin(bins, capacity);
//      }
//      place(piece, emptiest2);
//    });
  }

}