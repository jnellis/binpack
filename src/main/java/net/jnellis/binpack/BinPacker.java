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

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
abstract public class BinPacker<
    P extends Comparable<P>,
    C extends Comparable<C>> {


  /**
   * The ordering imposed upon the inputs (i.e. ascending, descending, etc,)
   */
  private PreOrderPolicy<P> preOrderPolicy = new DescendingPolicy<>();

  /**
   * The packing algorithm to use.
   */
  private PackingPolicy<P, C> packingPolicy = new BestFitPackingPolicy<>();

  /**
   * Fixed bin sizes that need to be filled first need an ordering policy also.
   */
  private PreOrderPolicy<Bin<P, C>> existingBinPreOrderPolicy =
      new DescendingPolicy<>();

  /**
   * An ordering policy for available bin sizes (not existing bins) for when
   * a piece needs a new bin.
   */
  private PreOrderPolicy<C> availableCapacitiesPreOrderPolicy =
      new AscendingPolicy<>();

  /**
   * Returns the PackingPolicy for choosing a bin before a pack.
   *
   * @return the current PackingPolicy
   */
  public PackingPolicy<P, C> getPackingPolicy() {

    return packingPolicy;
  }

  /**
   * Sets the packing algorithm to be used to fit pieces into bins. The default
   * PackingPolicy is {@link BestFitPackingPolicy}
   *
   * @param policy The algorithm to pack one piece into a set of bins.
   * @return Returns this BinPacker for use in chainable operations.
   * @exception NullPointerException if packingPolicy is null.
   */
  public BinPacker<P, C> setPackingPolicy(final PackingPolicy<P, C> policy) {

    Objects.requireNonNull(policy, "PackingPolicy can't be null.");
    this.packingPolicy = policy;
    return this;
  }

  /**
   * Returns the PreOrderPolicy that will be applied to existingBins before
   * the pack.
   *
   * @return the current PreOrderPolicy for existing bins.
   */
  public PreOrderPolicy<Bin<P, C>> getExistingBinPreOrderPolicy() {

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
  public BinPacker<P, C> setExistingBinPreOrderPolicy(
      final PreOrderPolicy<Bin<P, C>> preOrderPolicy) {


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
  public PreOrderPolicy<C> getAvailableCapacitiesPreOrderPolicy() {

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
  public BinPacker<P, C> setAvailableCapacitiesPreOrderPolicy(
      final PreOrderPolicy<C> preOrderPolicy) {

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
  public List<Bin<P, C>> packAll(final List<P> pieces,
                                 final List<Bin<P, C>> existingBins,
                                 final List<C> availableCapacities) {

    getPreOrderPolicy()
        .order(pieces)
        .forEach(
            getPackFunction( //returns a function that packs a piece.
                             // order the existing bins by max remaining
                             // capacity
                             existingBinPreOrderPolicy
                                 .order(existingBins),
                             // choose order capacities are tried.
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
  public PreOrderPolicy<P> getPreOrderPolicy() {

    return preOrderPolicy;
  }

  /**
   * Sets the ordering to be imposed on pieces. The method {@link #packAll}
   * will sort pieces by this ordering before packing. The default
   * is {@link net.jnellis.binpack.preorder.DescendingPolicy}.
   *
   * @param policy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   * @exception NullPointerException if preOrderPolicy is null.
   */
  public BinPacker<P, C> setPreOrderPolicy(final PreOrderPolicy<P> policy) {

    Objects.requireNonNull(policy, "PreOrderPolicy can't be null.");
    this.preOrderPolicy = policy;
    return this; // chainable
  }

  private Consumer<? super P> getPackFunction(final List<Bin<P, C>> bins,
                                              final List<C> capacities) {

    return (piece) -> pack(piece, bins, capacities);
  }

  /**
   * Attempts to place one {@code piece} into bins of {@code existingBins}
   * sizes first then uses {@code availableCapacities} bin sizes when it
   * needs to fill a new bin.
   *
   * @param piece               The pieces that need to be packed.
   * @param existingBins        Existing bins that pieces will be packed
   *                            into first.
   * @param availableCapacities Available bin sizes that we can create.
   * @return Returns the new existingBins.
   */
  public List<Bin<P, C>> pack(final P piece,
                              final List<Bin<P, C>> existingBins,
                              final List<C> availableCapacities) {

    final Supplier<Bin<P, C>> newBin =
        () -> addNewBin(piece, existingBins, availableCapacities);

    this.packingPolicy.chooseBin(piece, existingBins)
                      .orElseGet(newBin)
                      .add(piece);

    return existingBins;
  }

  /**
   * Add a new bin to the list of existing bins. New bin should be able to take
   * on available capacities. Implementations must not add piece to new bin.
   * Implementations should throw AssertionError if piece won't fit in a new bin
   * of any capacity specified.
   *
   * @param piece    The piece that wants to be added.
   * @param existingBins   New bins will be added to this list.
   * @param availableCapacities  Capacities for new bins.
   * @return the newly created bin.
   */
  abstract Bin<P, C> addNewBin(P piece,
                               List<Bin<P, C>> existingBins,
                               List<C> availableCapacities);


}
