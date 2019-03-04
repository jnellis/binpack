/*
 * Copyright (c) 2017.  Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 * BinPackCollector.java  lastModified: 8/7/17 2:59 PM
 */

package net.jnellis.binpack.collectors;

import net.jnellis.binpack.Bin;
import net.jnellis.binpack.CapacitySupport;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A Collector interface for packing pieces into bins.
 * <pre>{@code
 * List<Double> boardLengths = loadLengths();
 * List<Double> stockLengths = Collections.asList(8d, 12d, 16d);  //in feet
 *
 * Collection<LinearBin> bins =
 *     boardLengths.stream()
 *                 .collect(bestFitPacking( () -> new LinearBin(stockLengths),
 *                                          Function.identity()));
 *
 * }</pre>
 */
public interface BinPackCollector<
    PIECE extends Comparable<PIECE>,
    CAPACITY extends Comparable<CAPACITY>,
    BINTYPE extends Bin<PIECE, CAPACITY> &
        Comparable<Bin<PIECE, CAPACITY>> &
        CapacitySupport<CAPACITY>,
    A>
    extends Collector<PIECE, A, Collection<BINTYPE>> {



  /**
   * When packing pieces into bins, if there is no existing bin that will
   * fit, a new bin is requested via this {@link Supplier}.
   *
   * @return a function that supplies a new {@link Bin}.
   */
  Supplier<BINTYPE> newBin();

  /**
   * Converts a piece type to a capacity type for the purpose of comparing
   * a piece to a remaining capacity of a partially filled bin.
   *
   * @param piece piece type
   * @return This pieces representation as a capacity.
   */
  CAPACITY pieceAsCapacity(PIECE piece);

  @Override
  default Set<Characteristics> characteristics() {

    return Collections.emptySet();
  }

}
