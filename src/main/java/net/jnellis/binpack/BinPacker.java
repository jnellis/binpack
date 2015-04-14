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
import net.jnellis.binpack.preorder.AsIsPolicy;
import net.jnellis.binpack.preorder.AscendingPolicy;
import net.jnellis.binpack.preorder.DescendingPolicy;
import net.jnellis.binpack.preorder.PreOrderPolicy;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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
 * @see  SpliceableBinPacker
 */
//@formatter:on
abstract public class BinPacker<T extends Comparable<T>> {


  /**
   * The ordering imposed upon the inputs (i.e. ascending, descending, etc,)
   */
  private Optional<PreOrderPolicy<T>> preOrderPolicy =
      Optional.of(new DescendingPolicy<>());

  /**
   * The packing algorithm to use.
   */
  private Optional<PackingPolicy<T>> packingPolicy =
      Optional.of(new BestFitPackingPolicy<>());

  /**
   * Fixed bin sizes that need to be filled first need an ordering policy also.
   */
  private Optional<PreOrderPolicy<Bin<T>>> existingBinPreOrderPolicy =
      Optional.of(new DescendingPolicy<>());

  /**
   * An ordering policy for available bin sizes (not existing bins) for when
   * a piece needs a new bin.
   */
  private Optional<PreOrderPolicy<T>> availableCapacitiesPreOrderPolicy =
      Optional.of(new AscendingPolicy<>());

  /**
   * Returns the PreOrderPolicy for pieces before a pack.
   * @return the current PreOrderPolicy
   */
  public Optional<PreOrderPolicy<T>> getPreOrderPolicy() {
    return preOrderPolicy;
  }

  /**
   * Sets the ordering to be imposed on pieces. The method {@link #packAll}
   * will sort pieces by this ordering before packing. The default
   * is {@link net.jnellis.binpack.preorder.DescendingPolicy}.
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   */
  public BinPacker<T> setPreOrderPolicy(
      PreOrderPolicy<T> preOrderPolicy) {

    this.preOrderPolicy = Optional.ofNullable(preOrderPolicy);
    return this; // chainable
  }

  /**
   * Returns the PackingPolicy for choosing a bin before a pack.
   *
   * @return the current PackingPolicy
   */
  public Optional<PackingPolicy<T>> getPackingPolicy() {
    return packingPolicy;
  }

  /**
   * Sets the packing algorithm to be used to fit pieces into bins. The default
   * PackingPolicy is {@link BestFitPackingPolicy}
   *
   * @param packingPolicy The algorithm to pack one piece into a set of bins.
   * @return Returns this BinPacker for use in chainable operations.
   */
  public BinPacker<T> setPackingPolicy(
      PackingPolicy<T> packingPolicy) {

    this.packingPolicy = Optional.of(packingPolicy);
    return this;
  }

  /**
   * Returns the PreOrderPolicy that will be applied to existingBins before
   * the pack.
   *
   * @return the current PreOrderPolicy for existing bins.
   */
  public Optional<PreOrderPolicy<Bin<T>>> getExistingBinPreOrderPolicy() {
    return existingBinPreOrderPolicy;
  }

  /**
   * Sets the ordering to be imposed upon existing <code>Bin</code>s. The
   * method {@link #packAll} will sort bins by each bin's <code>compareTo</code>
   * method, preferably upon the bin's remaining capacity, with this policy.
   * The default is {@link net.jnellis.binpack.preorder.DescendingPolicy} which
   * represents an ordering of emptiest bins first.
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   */
  public BinPacker<T> setExistingBinPreOrderPolicy(
      PreOrderPolicy<Bin<T>> preOrderPolicy) {

    this.existingBinPreOrderPolicy = Optional.of(preOrderPolicy);
    return this; // chainable
  }

  /**
   * Returns the PreOrderPolicy that will be applied to available capacities
   * before the pack.
   *
   * @return the PreOrderPolicy for available bin capacities.
   */
  public Optional<PreOrderPolicy<T>> getAvailableCapacitiesPreOrderPolicy() {
    return availableCapacitiesPreOrderPolicy;
  }

  /**
   * Sets the ordering to be imposed upon available capacities of new
   * <code>Bin</code>s. The method {@link #packAll} will sort bin capacities
   * in the order they will be tried when trying to fit a piece in a new
   * bin. The default is {@link net.jnellis.binpack.preorder.AscendingPolicy}.
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   */
  public BinPacker<T> setAvailableCapacitiesPreOrderPolicy(
      PreOrderPolicy<T> preOrderPolicy) {

    this.availableCapacitiesPreOrderPolicy = Optional.of(preOrderPolicy);
    return this; // chainable
  }

  /**
   * Attempts to store <code>pieces</code> into bins, starting
   * with <code>existingBins</code>. When a new bin is required, one is copied
   * from the <code>availableCapacities</code> set according to the
   * <code>existingBinPreOrderPolicy</code>.
   *
   * @param pieces              List of pieces to be packed.
   * @param existingBins        The initial set of bins will be made with these
   *                            capacities.
   * @param availableCapacities An array of bin capacities we can make when we
   *                            need another bin.
   * @return Returns the modified list of existingBins
   */
  public List<Bin<T>> packAll(List<T> pieces,
                              List<Bin<T>> existingBins,
                              List<T> availableCapacities) {

    if (packingPolicy.isPresent()) {

      getPreOrderPolicy()
          .orElse(new AsIsPolicy<>())
          .order(pieces)
          .forEach(
              curriedPackFunction( //returns a function that packs a piece.
                                   // order the existing bins by max
                                   // remaining capacity
                                   existingBinPreOrderPolicy
                                       .orElse(new AsIsPolicy<>())
                                       .order(existingBins),
                                   //
                                   availableCapacitiesPreOrderPolicy
                                       .orElse(new AsIsPolicy<>())
                                       .order(availableCapacities))
          );
      return existingBins;

    } else {
      throw new NoSuchElementException("No PackingPolicy found for " +
          this.getClass().getName() + ".  Use this.setPackingPolicy()");
    }
  }

  private Consumer<? super T> curriedPackFunction(List<Bin<T>> orderedBins,
                                                  List<T> orderedCapacities) {
    return (piece) -> pack(piece, orderedBins, orderedCapacities);
  }

  /**
   * Attempts to place one <code>piece</code> into bins of <code>existing</code>
   * sizes first then uses <code>available</code> bin sizes when it needs to
   * fill a new bin.
   *
   * @param piece               The pieces that need to be packed.
   * @param existingBins        Existing bins that pieces will be packed
   *                            into first.
   * @param availableCapacities Available bin sizes that we can create.
   * @return Returns the new existingBins.
   */
  public List<Bin<T>> pack(T piece,
                           List<Bin<T>> existingBins,
                           List<T> availableCapacities) {

    this.packingPolicy.get()
                      .chooseBin(piece, existingBins)
                      .orElseGet(() -> addNewBin(existingBins,
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
  void assertPieceWouldFitInANewBin(T piece, List<T> capacities) {

    if (piece.compareTo(Collections.max(capacities)) > 0) { // Houston!
      // we could check here to see if an existing bin is big enough
      // and then reorder that bin, if possible, and use this piece there.
      throw new AssertionError("Piece is bigger than the maximum " +
                                   "available capacity of a new bin.");
    }
  }
}
