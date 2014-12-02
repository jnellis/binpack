/*
 * FirstFitPackingPolicy.java
 *
 * Created on August 19, 2006, 7:21 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;

/**
 * Places pieces in the closest bin first.
 * @author Joe Nellis
 */
public interface FirstFitPackingPolicy<T extends Comparable<T> >
    extends LinearPackingPolicy<T> {

  @Override
  default public void pack(List<T> pieces, List<Bin<T>> bins, T capacity) {
    // first fit - place a new piece in the leftmost bin that still has room.

    pieces.forEach(piece -> {
      Bin<T> firstAvailableBin = bins.stream()
          .filter(bin -> bin.canFit(piece))
          .findFirst()
          .orElse(createNewBin(bins, capacity));

      assert firstAvailableBin.canFit(piece);
      place(piece, firstAvailableBin);
    });
  }


}
