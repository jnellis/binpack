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
import java.util.function.Function;
import java.util.function.Predicate;
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
   * Packs pieces by choosing the fullest bin that still
   * has remaining capacity enough to fit the next piece.
   * When multiple bins qualify with the same remaining
   * capacity, the bin that has stayed the longest at that
   * capacity is chosen.
   * <p>
   * This is a convenience factory method for avoiding variable
   * declarations of a verbose nature as this collector has
   * three type parameters that are implicitly defined
   * by the type signature of the method parameters.
   * </p>
   * <pre>{@code
   * List<Double> pieces =
   *     getIncomingPieces().stream()
   *                        .collect( bestFitPacking(
   *                            () -> new LinearBin (stockLengths),
   *                            Function.identity()));
   *
   * }</pre>
   *
   * @param newBinSupplier          Supplies new bins when needed.
   * @param pieceAsCapacityFunction Piece to Capacity conversion function
   * @param <PIECE>                 The type of piece you are trying to fit
   *                                into bins
   * @param <CAPACITY>              The type of capacity that represents
   *                                aggregate pieces.
   * @param <BINTYPE>               The type of bin that has CAPACITY and
   *                                takes PIECES.
   * @return the created collector
   */
  static <PIECE extends Comparable<PIECE>,
      CAPACITY extends Comparable<CAPACITY>,
      BINTYPE extends Bin<PIECE, CAPACITY> &
          Comparable<Bin<PIECE, CAPACITY>> &
          CapacitySupport<CAPACITY>>
  Collector<PIECE, ?, Collection<BINTYPE>> bestFitPacking(
      final Supplier<BINTYPE> newBinSupplier,
      final Function<PIECE, CAPACITY> pieceAsCapacityFunction
  ) {

    return new BestFitPackingCollector<>(
        newBinSupplier,
        pieceAsCapacityFunction);
  }

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

  /**
   * Creates a predicate for bins that can fit this particular piece.
   * <p>
   * Usage:
   * <pre>
   *   collection.stream()
   *             .filter(binsThatCanFit(piece);
   * </pre>
   *
   * @param piece The piece that will be packed next.
   * @return Predicate for checking if a bin fits the given piece.
   */
  default Predicate<BINTYPE> binsThatCanFit(final PIECE piece) {

    return bin -> bin.canFit(piece);
  }

}
