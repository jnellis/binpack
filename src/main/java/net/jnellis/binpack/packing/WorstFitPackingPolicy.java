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
import net.jnellis.binpack.BinImpl;
import net.jnellis.binpack.LinearBin;

import java.util.List;

/**
 * Places pieces into the emptiest bin first.
 *
 * @author Joe Nellis
 */
public interface WorstFitPackingPolicy<T extends Comparable<T> >
    extends LinearPackingPolicy<T> {

  @Override
  default void pack(List<T> pieces,
                    List<Bin<T>> bins,
                    T capacity) {

    pieces.forEach(piece -> {
      LinearBin<T> emptiest = new BinImpl<T>(capacity);
      // find the emptiest bin or create a new one.
      emptiest = bins.stream().min(emptiest::compare)
          .orElse(createNewBin(bins, capacity));

      assert emptiest.canFit(piece);
      place(piece, emptiest);
    });
  }


}
