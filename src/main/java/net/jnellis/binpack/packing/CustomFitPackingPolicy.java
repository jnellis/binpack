/*
 * CustomFitPackingPolicy.java
 *
 * Created on September 4, 2006, 3:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;

/**
 * Alternates placing pieces from front then back.
 *
 * @author Joe Nellis
 */
public class CustomFitPackingPolicy    implements LinearPackingPolicy  {
  @Override
  public List<Bin<Double>> pack(Double piece, List<Bin<Double>>
      existingBins, List<Double> availableCapacities) {
    return null;
//
//    boolean toggle = false;
//    for (int i = 0; i < pieces.size(); i+n+) {
//      double piece = pieces.get(i);
//      LinkedList<Double> onePiece = new LinkedList<>();
//      onePiece.add(piece);
//      FirstFitPackingPolicy<Double> firstFitPackingPolicy = (FirstFitPackingPolicy<Double>)this;
//      toggle ^= true; //xor with true
//    }
//      firstFitPackingPolicy.pack(onePiece,bins,capacity);
  }

}
