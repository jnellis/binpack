/*
 * SpliceableBinPacker.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A  {@link LinearBinPacker}  that pre-processes input pieces
 * before packing. Each piece that is larger than the maximum available
 * capacity of a new bin (not an existing bin), is broken up into pieces
 * of that maximum capacity size, plus a remainder piece, if one exists, that
 * is smaller than the maximum capacity.
 * <p>
 * The dividing of pieces too big takes place in-order and before the call to
 * order pieces by the set preOrderPolicy before packing.
 */
public class SpliceableBinPacker extends LinearBinPacker {


  @Override
  public List<Bin<Double, Double>> packAll(final List<Double> pieces,
                                           final List<Bin<Double, Double>>
                                               existingBins,
                                           final List<Double>
                                               availableCapacities) {

    return super.packAll(
        createSplicePieces(pieces, Collections.max(availableCapacities)),
        existingBins,
        availableCapacities);
  }

  /**
   * Finds and replaces pieces that are longer than the maximum capacity
   * with however many maximum capacity sized pieces plus a remainder piece.
   * Natural order is maintained with pieces exploded as they are
   * iterated through.
   *
   * @param pieces      list of pieces to pack
   * @param maxCapacity list of available maxCapacity.
   * @return Returns the new <i>pieces</i> stream.
   */
  public static List<Double> createSplicePieces(final Iterable<Double> pieces,
                                                final Double maxCapacity) {

    final List<Double> newPieces = new ArrayList<>();
    for (Double piece : pieces) {
      while (piece > maxCapacity) {
        piece = piece - maxCapacity;
        newPieces.add(maxCapacity);
      }
      if (piece > 0.0) {
        newPieces.add(piece);
      }
    }
    return newPieces;
  }


}