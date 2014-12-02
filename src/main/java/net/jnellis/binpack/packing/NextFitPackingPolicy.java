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
import java.util.ListIterator;

/**
 * Places pieces in last bin or a new bin.
 * @author Joe Nellis
 */
public interface NextFitPackingPolicy<T extends Comparable<T> >
    extends LinearPackingPolicy<T> {

  @Override
  default void pack(List<T> pieces, List<Bin<T>> bins, T capacity) {
    pieces.forEach(piece -> {
      Bin<T> lastBin = null;
      ListIterator<Bin<T>> iterator = bins.listIterator();
      if (iterator.hasPrevious()) {
        lastBin = iterator.previous();
      }
      if(lastBin == null || !lastBin.canFit(piece)) {
        lastBin = createNewBin(bins, capacity);
      }
      place(piece,lastBin);
    });
  }


}
