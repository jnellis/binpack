/*
 * BinPacker.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import net.jnellis.binpack.packing.BestFitPackingPolicy;
import net.jnellis.binpack.packing.PackingPolicy;
import net.jnellis.binpack.preorder.AscendingPolicy;
import net.jnellis.binpack.preorder.DescendingPolicy;
import net.jnellis.binpack.preorder.PreOrderPolicy;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

//@formatter:off

/**
 * BinPacker is a processor of packing operations.
 * <pre>{@code
 * List<Double> pieces = Arrays.asList(3d, 4d, 8d, 5d, 7d);
 * List<Bin<Double>> bins = new ArrayList<>();
 * // bins are only 6.0 long
 * List<Double> capacities = Arrays.asList(6d);
 * BinPacker<Double> binPacker = new SpliceableBinPacker();
 * bins = binPacker.setPreOrderPolicy(new DescendingPolicy<>())
 *                 .setPackingPolicy(new BestFitPackingPolicy<>())
 *                 .setAvailableCapacitiesPreOrderPolicy(
 *                    new DescendingPolicy<>())
 *                 .packAll(pieces, bins, capacities);
 *  }</pre>
 *
 * @see SpliceableBinPacker
 */
//@formatter:on
abstract public class BinPacker<T extends Comparable<T>> {


  /**
   * The ordering imposed upon the inputs (i.e. ascending, descending, etc,)
   */
  private PreOrderPolicy<T> preOrderPolicy =
      new DescendingPolicy<>();

  /**
   * The packing algorithm to use.
   */
  private PackingPolicy<T> packingPolicy =
      new BestFitPackingPolicy<>();

  /**
   * Fixed bin sizes that need to be filled first need an ordering policy also.
   */
  private PreOrderPolicy<Bin<T>> existingBinPreOrderPolicy =
      new DescendingPolicy<>();

  /**
   * An ordering policy for available bin sizes (not existing bins) for when
   * a piece needs a new bin.
   */
  private PreOrderPolicy<T> availableCapacitiesPreOrderPolicy =
      new AscendingPolicy<>();

  /**
   * Returns the PackingPolicy for choosing a bin before a pack.
   *
   * @return the current PackingPolicy
   */
  public PackingPolicy<T> getPackingPolicy() {

    return packingPolicy;
  }

  /**
   * Sets the packing algorithm to be used to fit pieces into bins. The default
   * PackingPolicy is {@link BestFitPackingPolicy}
   *
   * @param packingPolicy The algorithm to pack one piece into a set of bins.
   * @return Returns this BinPacker for use in chainable operations.
   * @exception NullPointerException if packingPolicy is null.
   */
  public BinPacker<T> setPackingPolicy(final PackingPolicy<T> packingPolicy) {

    Objects.requireNonNull(packingPolicy, "PackingPolicy can't be null.");
    this.packingPolicy = packingPolicy;
    return this;
  }

  /**
   * Returns the PreOrderPolicy that will be applied to existingBins before
   * the pack.
   *
   * @return the current PreOrderPolicy for existing bins.
   */
  public PreOrderPolicy<Bin<T>> getExistingBinPreOrderPolicy() {

    return existingBinPreOrderPolicy;
  }

  /**
   * Sets the ordering to be imposed upon existing {@code Bin}s. The
   * method {@link #packAll} will sort bins by each bin's {@code compareTo}
   * method, preferably upon the bin's remaining capacity, with this policy.
   * The default is {@link net.jnellis.binpack.preorder.DescendingPolicy} which
   * represents an ordering of emptiest bins first.
   *
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   * @exception NullPointerException if preOrderPolicy is null.
   */
  public BinPacker<T> setExistingBinPreOrderPolicy(
      final PreOrderPolicy<Bin<T>> preOrderPolicy) {


    Objects.requireNonNull(preOrderPolicy, "PreOrderPolicy can't be null.");
    this.existingBinPreOrderPolicy = preOrderPolicy;
    return this; // chainable
  }

  /**
   * Returns the PreOrderPolicy that will be applied to available capacities
   * before the pack.
   *
   * @return the PreOrderPolicy for available bin capacities.
   */
  public PreOrderPolicy<T> getAvailableCapacitiesPreOrderPolicy() {

    return availableCapacitiesPreOrderPolicy;
  }

  /**
   * Sets the ordering to be imposed upon available capacities of new
   * {@code Bin}s. The method {@link #packAll} will sort bin capacities
   * in the order they will be tried when trying to fit a piece in a new
   * bin. The default is {@link net.jnellis.binpack.preorder.AscendingPolicy}.
   *
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   * @exception NullPointerException if perOrderPolicy is null.
   */
  public BinPacker<T> setAvailableCapacitiesPreOrderPolicy(
      final PreOrderPolicy<T> preOrderPolicy) {

    Objects.requireNonNull(preOrderPolicy, "PreOrderPolicy can't be null.");
    this.availableCapacitiesPreOrderPolicy = preOrderPolicy;
    return this; // chainable
  }

  /**
   * Attempts to store {@code pieces} into bins, starting
   * with {@code existingBins}. When a new bin is required, one is copied
   * from the {@code availableCapacities} set according to the
   * {@code existingBinPreOrderPolicy}.
   *
   * @param pieces              List of pieces to be packed.
   * @param existingBins        The initial set of bins will be made with these
   *                            capacities.
   * @param availableCapacities An array of bin capacities we can make when we
   *                            need another bin.
   * @return Returns the modified list of existingBins
   */
  public List<Bin<T>> packAll(final List<T> pieces,
                              final List<Bin<T>> existingBins,
                              final List<T> availableCapacities) {


    getPreOrderPolicy()
        .order(pieces)
        .forEach(
            getPackFunction( //returns a function that packs a piece.
                             // order the existing bins by max
                             // remaining capacity
                             existingBinPreOrderPolicy
                                 .order(existingBins),
                             //
                             availableCapacitiesPreOrderPolicy
                                 .order(availableCapacities))
        );
    return existingBins;
  }

  /**
   * Returns the PreOrderPolicy for pieces before a pack.
   *
   * @return the current PreOrderPolicy
   */
  public PreOrderPolicy<T> getPreOrderPolicy() {

    return preOrderPolicy;
  }

  /**
   * Sets the ordering to be imposed on pieces. The method {@link #packAll}
   * will sort pieces by this ordering before packing. The default
   * is {@link net.jnellis.binpack.preorder.DescendingPolicy}.
   *
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   * @exception NullPointerException if preOrderPolicy is null.
   */
  public BinPacker<T> setPreOrderPolicy(
      final PreOrderPolicy<T> preOrderPolicy) {

    Objects.requireNonNull(preOrderPolicy, "PreOrderPolicy can't be null.");
    this.preOrderPolicy = preOrderPolicy;
    return this; // chainable
  }

  private Consumer<? super T> getPackFunction(final List<Bin<T>> bins,
                                              final List<T> capacities) {

    return (piece) -> pack(piece, bins, capacities);
  }

  /**
   * Attempts to place one {@code piece} into bins of {@code existing}
   * sizes first then uses {@code available} bin sizes when it needs to
   * fill a new bin.
   *
   * @param piece               The pieces that need to be packed.
   * @param existingBins        Existing bins that pieces will be packed
   *                            into first.
   * @param availableCapacities Available bin sizes that we can create.
   * @return Returns the new existingBins.
   */
  public List<Bin<T>> pack(final T piece,
                           final List<Bin<T>> existingBins,
                           final List<T> availableCapacities) {

    this.packingPolicy.chooseBin(piece, existingBins)
                      .orElseGet(() -> addNewBin(
                          existingBins,
                          piece,
                          availableCapacities))
                      .add(piece);
    return existingBins;
  }

  abstract Bin<T> addNewBin(List<Bin<T>> existingBins,
                            T piece,
                            List<T> availableCapacities);


  /**
   * Asserts that if a new bin was created, based on the max capacity of the
   * new bin, the piece would still fit.
   *
   * @param piece      The piece that will be packed later.
   * @param capacities The potential capacities of a new bin.
   */
  void assertPieceWouldFitInANewBin(final T piece, final List<T> capacities) {

    if (piece.compareTo(Collections.max(capacities)) > 0) { // Houston!
      // we could check here to see if an existing bin is big enough
      // and then reorder that bin, if possible, and use this piece there.
      throw new AssertionError("Piece is bigger than the maximum " +
                                   "available capacity of a new bin.");
    }
  }
}
