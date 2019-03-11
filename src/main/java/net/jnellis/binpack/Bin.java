/*
 * Bin.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import java.util.List;

/**
 * A packing bin used by {@link BinPacker}. A bin has a <b>capacity</b>, can
 * contain pieces, has a means to determine if a piece can fit in the bin and
 * finally if the bin was 'existing.' An existing bin means it was not created
 * during the packing operation but exists as a given bin with the intention
 * that it represents results from a previous or partial packing operation.
 */
public interface Bin<
    P extends Comparable<P>,
    C extends Comparable<C>>
    extends Comparable<Bin<P, C>>, CapacitySupport<C> {


  /**
   * Add a piece to this bin. The piece should be checked by calling {@link
   * #canFit } first.
   *
   * @param piece The piece to add.
   * @return true if the piece was added.
   */
  boolean add(P piece);

  /**
   * Compare two bins based on their maximum remaining capacity. 
   *
   * @see #getMaxRemainingCapacity
   */
  @Override
  default int compareTo(final Bin<P, C> o) {

    return getMaxRemainingCapacity().compareTo(o.getMaxRemainingCapacity());
  }


  /**
   * Determines if the offered piece will fit in the bin.
   *
   * @param piece The piece to fit.
   * @return true if the piece will fit.
   */
  boolean canFit(P piece);

  /**
   * Returns whether this bin is an existing bin and not created on the fly
   * during a previous packing operation.
   *
   * @return true if this is an existing bin.
   */
  boolean isExisting();


  /**
   * The pieces contained in the bin.
   *
   * @return an unmodifiable list of current pieces in this bin.
   */
  List<P> getPieces();


}
