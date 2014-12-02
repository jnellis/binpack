/*
 * PackingPolicy.java
 *
 * Created on August 19, 2006, 6:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.stream.Stream;

/**
 * A packing algorithm that attempts to fit the most pieces in the least
 * amount of bins of a specified size.
 *
 * @author Joe Nellis
 */
@FunctionalInterface
public interface PackingPolicy<T> {

  /**
   * Attempts to place <code>pieces</code> into bins of <code>existing</code>
   * sizes first then uses <code>available</code> bin sizes when it needs to
   * fill a new bin.
   *
   * @param pieces              The pieces that need to be packed.
   * @param existingCapacities  Existing bin sizes that pieces will be packed
   *                            into first.
   * @param availableCapacities Available bin sizes that we can create.
   * @return Returns a stream of packed bins.
   */
  public Stream<Bin<T>> pack(Stream<T> pieces,
                             Stream<T> existingCapacities,
                             Stream<T> availableCapacities);
}
