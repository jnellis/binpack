package net.jnellis.binpack;

import net.jnellis.binpack.packing.PackingPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.*;

/**
 * User: Joe
 * Date: 12/1/2014
 * Time: 6:27 PM
 */
public class SplicableBinPacker extends BinPacker<Double> {


  @Override
  public BinPacker<Double> setPackingPolicy(PackingPolicy<Double>
                                                packingPolicy) {
    this.packingPolicy = Optional.of(packingPolicy);
    return null;
  }

  @Override
  public List<Bin<Double>> packAll(List<Double> pieces,
                                   List<Bin<Double>> existingBins,
                                   List<Double> availableCapacities) {

    return super.packAll(createSplicePieces(pieces, availableCapacities),
        existingBins,
        availableCapacities);
  }


  /**
   * Finds and replaces pieces that are longer than the maximum capacity
   * with however many maximum capacity sized pieces plus a remainder piece.
   * General order is maintained with pieces expanded as they are
   * iterated through.
   *
   * @param pieces     list of pieces to pack
   * @param capacities list of available capacities.
   * @return Returns the new <i>pieces</i> stream.
   */
  public List<Double> createSplicePieces(List<Double> pieces,
                                         List<Double> capacities) {

    Double maxCapacity = Collections.max(capacities);
    // copy this stream and break up long piece by the max available
    // capacity.
    List<Double> newPieces = new ArrayList<>();
    for (Double piece : pieces) {
      if (piece > maxCapacity) {
        // compute num of divisors, the remainder.
        int divisions = toIntExact(round(floor(piece / maxCapacity)));
        double remainder = piece - divisions * maxCapacity;
        while (divisions-- > 0) {
          newPieces.add(maxCapacity);
        }
        newPieces.add(remainder);
      } else {
        newPieces.add(piece);
      }
    }
    return newPieces;
  }


}