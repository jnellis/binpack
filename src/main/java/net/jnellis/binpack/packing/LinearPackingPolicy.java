/*
 * BasicPackingPolicy.java
 *
 * Created on September 3, 2006, 4:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.LinearBin;

import java.util.List;

/**
 * A packing policy for linear sizes. Assumes piece sizes are of type Double.
 *
 * @author Joe Nellis
 */
public interface LinearPackingPolicy extends PackingPolicy<Double> {

  /**
   * Attempt to insert this piece into the bin.
   *
   * @param piece The piece to be placed.
   * @param bin   The bin to hold the piece.
   * @return Returns whether it was successful.
   */
  default boolean place(Double piece, LinearBin bin) {
    boolean result = false;
    if (bin.canFit(piece)) {
      result = bin.add(piece);
    }
    return result;
  }

  default LinearBin createNewBin(List<LinearBin> bins, List<Double>
      capacities) {
    LinearBin newBin = new LinearBin(capacities);
    // add it to existing bins
    bins.add(newBin);
    return newBin;
  }

}
