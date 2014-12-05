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
public class FirstFitPackingPolicy implements LinearPackingPolicy {

  /**
   * Attempts to place <code>pieces</code> into bins of <code>existing</code>
   * sizes first then uses <code>available</code> bin sizes when it needs to
   * fill a new bin.
   *
   * @param piece              The pieces that need to be packed.
   * @param existingBins        Existing bins that pieces will be packed
   *                            into first.
   * @param availableCapacities Available bin sizes that we can create.
   * @return Returns a stream of packed bins.
   */
  @Override
  public List<Bin<Double>> pack(Double piece,
                           List<Bin<Double>> existingBins,
                           List<Double> availableCapacities) {


    // first fit - place a new piece in the leftmost bin that still has room.
//    pieces.forEach(piece -> {
//      Optional<Bin<Double>> firstAvailableBin = existingBins
//          .filter(bin -> bin.canFit(piece))
//          .findFirst();
//         // .orElse(createNewBin(existingBins, availableCapacities).);
//
//      assert firstAvailableBin.get().canFit(piece);
//
//      place(piece, firstAvailableBin.get());
//    });

    return existingBins;
  }


}
