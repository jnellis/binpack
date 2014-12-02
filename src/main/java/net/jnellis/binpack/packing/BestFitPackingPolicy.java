/*
 * BestFitPackingPolicy.java
 *
 * Created on August 19, 2006, 6:57 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;
import net.jnellis.binpack.LinearBin;

import java.util.stream.Stream;

/**
 *  Places a piece in fullest bin that has space.
 * @author Joe Nellis
 */
public class BestFitPackingPolicy implements LinearPackingPolicy {


  @Override
  public Stream<Bin<Double>> pack(Stream<Double> pieces, Stream<Double>
      existingCapacities, Stream<Double> availableCapacities) {



    pieces.forEach(piece->{
       LinearBin fullestFittableBin = bins.stream().filter(bin-> bin.canFit(piece))
          .sorted()
          .findFirst()
          .orElse(createNewBin(bins,capacity));
      place(piece,fullestFittableBin);
    });
  }
}
