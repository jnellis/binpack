/*
 * LastFitPackingPolicy.java
 *
 * Created on September 3, 2006, 5:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.stream.Stream;

/**
 * Places pieces in the farthest bin first.
 *
 * @author Joe Nellis
 */
public class LastFitPackingPolicy implements LinearPackingPolicy  {

  @Override
  public Stream<Bin<Double>> pack(Stream<Double> pieces, Stream<Bin<Double>>
      existingBins, Stream<Double> availableCapacities) {
    return null;
//    pieces.forEach(piece -> {
//      ListIterator<Bin<T>> iterator = bins.listIterator();
//      while (iterator.hasPrevious()) {
//        Bin<T> lastAvailableBin = iterator.previous();
//        if (lastAvailableBin.canFit(piece)) {
//          place(piece, lastAvailableBin);
//          return; // leave early
//        }
//      }
//      // no existing bins has space.
//      Bin<T> newBin = createNewBin(bins, capacity);
//      place(piece, newBin);
//    });
  }
}
