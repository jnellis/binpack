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
import net.jnellis.binpack.LinearBin;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Alternates placing pieces from front then back.
 *
 * @author Joe Nellis
 */
public class CustomFitPackingPolicy    extends LinearPackingPolicy  {

  @Override
  public Stream<LinearBin> pack(Stream<Double> pieces, List<LinearBin> bins,Stream<Double> capacity) {
    boolean toggle = false;
    for (int i = 0; i < pieces.size(); i++) {
      double piece = pieces.get(i);
      LinkedList<Double> onePiece = new LinkedList<>();
      onePiece.add(piece);
      FirstFitPackingPolicy<T> firstFitPackingPolicy = (FirstFitPackingPolicy<T>)this;
      firstFitPackingPolicy.pack(onePiece,bins,capacity);
      toggle ^= true; //xor with true
    }
  }

}
