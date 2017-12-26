/*
 * Copyright (c) 2017.  Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 * BestFitPackingCollector.java  lastModified: 8/7/17 7:28 PM
 */

package net.jnellis.binpack.collectors;

import net.jnellis.binpack.Bin;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
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
    PIECE extends Comparable<PIECE>,
    CAPACITY extends Comparable<CAPACITY>,
    BINTYPE extends Bin<PIECE, CAPACITY>>
    implements BinPackCollector<PIECE, CAPACITY, BINTYPE,
    NavigableMap<CAPACITY, LinkedList<BINTYPE>>> {


  private final Supplier<BINTYPE> newBinSupplier;

  private final Function<PIECE, CAPACITY> pieceAsCapacity;

  /**
   * Collects pieces into bins. New bins are provided by the supplier when
   * requested.
   *
   * @param newBinSupplier  Supplies new bins, capacities are predetermined.
   * @param pieceAsCapacity Method to convert a piece type to a capacity type.
   */
  public BestFitPackingCollector(
      final Supplier<BINTYPE> newBinSupplier,
      final Function<PIECE, CAPACITY> pieceAsCapacity) {

    this.newBinSupplier = newBinSupplier;
    this.pieceAsCapacity = pieceAsCapacity;
  }


  /**
   * Treemap implementation where keys are 'remaining capacity' of each bin.
   *
   * @param binTree   The treemap, keys are remaining capacity, values are
   *                  a queue of bins with that remaining capacity.
   * @param piece     The piece to pack
   */
  private void binpackTree2(
      final NavigableMap<CAPACITY, LinkedList<BINTYPE>> binTree,
      final PIECE piece) {

    final CAPACITY key = pieceAsCapacity(piece);

    // Retrieve the key of the bins that have the smallest remaining capacity
    // at least enough to fit the piece.
    final Map.Entry<CAPACITY, LinkedList<BINTYPE>> entry =
        binTree.ceilingEntry(key);

    if (entry == null) {
      addNewEntry(binTree, piece);
    } else {
      addToExistingList(binTree, entry, piece);
    }
  }

  private void addNewEntry(final NavigableMap<CAPACITY, LinkedList<BINTYPE>>
                               binTree,
                           final PIECE piece) {

    // create a new bin and a list to store other bins that have this key.
    final BINTYPE bin = newBin().get();
    bin.add(piece);
    final LinkedList<BINTYPE> bList = new LinkedList<>();
    bList.add(bin);
    // store this new bin list.
    binTree.put(bin.getMaxRemainingCapacity(), bList);

  }

  private void addToExistingList(
      final NavigableMap<CAPACITY, LinkedList<BINTYPE>> binTree,
      final Map.Entry<CAPACITY, LinkedList<BINTYPE>> entry,
      final PIECE piece) {

    // get the list of bins with key space remaining.
    final LinkedList<BINTYPE> bList = entry.getValue();
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
    final CAPACITY newKey = bin.getMaxRemainingCapacity();
    binTree.computeIfAbsent(newKey, donotcare -> new LinkedList<>())
           .add(bin);
  }

  @Override
  public Supplier<BINTYPE> newBin() {

    return newBinSupplier;
  }

  @Override
  public CAPACITY pieceAsCapacity(final PIECE piece) {

    return pieceAsCapacity.apply(piece);
  }

  @Override
  public Supplier<NavigableMap<CAPACITY, LinkedList<BINTYPE>>> supplier() {

    return TreeMap::new;
  }

  @Override
  public BiConsumer<NavigableMap<CAPACITY, LinkedList<BINTYPE>>, PIECE>
  accumulator() {

    return this::binpackTree2;
  }

  @Override
  public BinaryOperator<NavigableMap<CAPACITY, LinkedList<BINTYPE>>>
  combiner() {

    return (bins, bins2) -> {
      bins.putAll(bins2);
      return bins;
    };
  }

  @Override
  public Function<NavigableMap<CAPACITY, LinkedList<BINTYPE>>,
      Collection<BINTYPE>> finisher() {

    return (binTree) -> binTree.values().stream()
                               .flatMap(LinkedList::stream)
                               .collect(Collectors.toList());
  }
}
