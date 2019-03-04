/*
 * Copyright (c) 2017.  Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 * BestFitPackingCollector.java  lastModified: 8/7/17 7:28 PM
 */

package net.jnellis.binpack.collectors;

import net.jnellis.binpack.Bin;
import net.jnellis.binpack.CapacitySupport;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A collector that chooses a bin for each piece in the stream based on the
 * best fit algorithm, choosing the fullest bin that will still
 * fit the given piece. The collector takes a 'bin' supplier for creating new
 * bins when there is no bin that will fit into the current bins.  Internally,
 * capacities of each bin are indexed by remaining capacity, which facilitates
 * a need to represent the piece being fit as a matching capacity to be compared
 * when traversing the index. This is supplied by the caller as a one to one
 * function taking a piece type and returning a capacity type.
 */
public class BestFitPackingCollector<
    P extends Comparable<P>,
    C extends Comparable<C>,
    BINTYPE extends Bin<P, C>>
    implements BinPackCollector<P, C, BINTYPE,
    NavigableMap<C, ArrayDeque<BINTYPE>>> {


  private final Supplier<BINTYPE> newBinSupplier;

  private final Function<P, C> pieceAsCapacity;

  /**
   * Collects pieces into bins. New bins are provided by the supplier when
   * requested.
   *
   * @param newBinSupplier  Supplies new bins, capacities are predetermined.
   * @param pieceAsCapacity Method to convert a piece type to a capacity type.
   */
  public BestFitPackingCollector(
      final Supplier<BINTYPE> newBinSupplier,
      final Function<P, C> pieceAsCapacity) {

    this.newBinSupplier = newBinSupplier;
    this.pieceAsCapacity = pieceAsCapacity;
  }

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
   * @param <BINTYPE>               The type of bin that has C and
   *                                takes PIECES.
   * @return the created collector
   */
  public static <PIECE extends Comparable<PIECE>,
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

  @Override
  public Supplier<NavigableMap<C, ArrayDeque<BINTYPE>>> supplier() {

    return TreeMap::new;
  }

  @Override
  public BiConsumer<NavigableMap<C, ArrayDeque<BINTYPE>>, P> accumulator() {

    return this::binpackTree2;
  }

  /**
   * Treemap implementation where keys are 'remaining capacity' of each bin.
   *
   * @param binTree The treemap, keys are remaining capacity, values are
   *                a queue of bins with that remaining capacity.
   * @param piece   The piece to pack
   */
  private void binpackTree2(
      final NavigableMap<C, ArrayDeque<BINTYPE>> binTree,
      final P piece) {

    final C key = pieceAsCapacity(piece);

    // Retrieve the key of the bins that have the smallest remaining capacity
    // at least enough to fit the piece.
    final Map.Entry<C, ArrayDeque<BINTYPE>> entry =
        binTree.ceilingEntry(key);

    if (entry == null) {
      addNewEntry(binTree, piece);
    } else {
      addToExistingList(binTree, entry, piece);
    }
  }

  private void addNewEntry(final NavigableMap<C, ArrayDeque<BINTYPE>> binTree,
                           final P piece) {

    // create a new bin and a list to store other bins that have this key.
    final BINTYPE bin = newBin().get();
    bin.add(piece);
    final ArrayDeque<BINTYPE> bList = new ArrayDeque<>();
    bList.add(bin);
    // store this new bin list.
    binTree.put(bin.getMaxRemainingCapacity(), bList);

  }

  private void addToExistingList(
      final NavigableMap<C, ArrayDeque<BINTYPE>> binTree,
      final Map.Entry<C, ArrayDeque<BINTYPE>> entry,
      final P piece) {

    // get the list of bins with key space remaining.
    final ArrayDeque<BINTYPE> bList = entry.getValue();
    assert bList != null && !bList.isEmpty();

    // the first bin in the list will do
    final BINTYPE bin = bList.poll();
    assert bin != null;
    if (bList.isEmpty()) {
      //remove empty lists immediately
      binTree.remove(entry.getKey());
    }
    assert bin.canFit(piece);
    // add piece to bin then reinsert to tree based on new key
    bin.add(piece);
    final C newKey = bin.getMaxRemainingCapacity();
    binTree.computeIfAbsent(newKey, donotcare -> new ArrayDeque<>())
           .add(bin);
  }

  @Override
  public Supplier<BINTYPE> newBin() {

    return newBinSupplier;
  }

  @Override
  public C pieceAsCapacity(final P piece) {

    return pieceAsCapacity.apply(piece);
  }

  @Override
  public BinaryOperator<NavigableMap<C, ArrayDeque<BINTYPE>>> combiner() {

    return (bins, bins2) -> {
      bins.putAll(bins2);
      return bins;
    };
  }

  @Override
  public Function<NavigableMap<C, ArrayDeque<BINTYPE>>,
      Collection<BINTYPE>> finisher() {

    return (binTree) -> binTree.values().stream()
                               .flatMap(ArrayDeque::stream)
                               .collect(Collectors.toList());
  }
}
