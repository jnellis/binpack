/*
 * BinPacker.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack;

import net.jnellis.binpack.packing.PackingPolicy;
import net.jnellis.binpack.preorder.AsIsPolicy;
import net.jnellis.binpack.preorder.PreOrderPolicy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * BinPacker is a {@link PackingPolicy} and a processor of packing operations.
 * <pre>{@code
 *     List<Double> pieces = Arrays.asList(3d, 4d, 8d, 5d, 7d); //piece lengths
 *     List<Bin<Double>> bins = Collections.emptyList();
 *     List<Double> capacities = Arrays.asList(6d);// bins are only 6.0 long
 *     BinPacker<Double> binPacker = new SpliceableBinPacker();
 *     bins = binPacker.setPreOrderPolicy(new DescendingPolicy<>())
 *         .setPackingPolicy(new BestFitPackingPolicy<>())
 *         .setAvailableCapacitiesPreOrderPolicy(new DescendingPolicy<>())
 *         .packAll(pieces, bins, capacities);
 * }</pre>
 *
 * @see  SpliceableBinPacker
 */
abstract public class BinPacker<T extends Comparable<T>>
    implements PackingPolicy<T> {

  /**
   * The ordering imposed upon the inputs (i.e. ascending, descending, etc,)
   */
  private Optional<PreOrderPolicy<T>> preOrderPolicy = Optional.empty();

  /**
   * The packing algorithm to use.
   */
  protected Optional<PackingPolicy<T>> packingPolicy = Optional.empty();

  /**
   * Fixed bin sizes that need to be filled first need an ordering policy also.
   */
  private Optional<PreOrderPolicy<Bin<T>>>
      existingBinPreOrderPolicy = Optional.empty();

  /**
   * An ordering policy for available bin sizes (not existing bins) for when
   * a piece needs a new bin.
   */
  private Optional<PreOrderPolicy<T>>
      availableCapacitiesPreOrderPolicy = Optional.empty();

  /**
   * Sets the ordering to be imposed on pieces. The method {@link #packAll}
   * will sort pieces by this ordering before packing.  A suggested default
   * would be {@link net.jnellis.binpack.preorder.DescendingPolicy}.
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   */
  public BinPacker<T> setPreOrderPolicy(PreOrderPolicy<T> preOrderPolicy) {
    this.preOrderPolicy = Optional.ofNullable(preOrderPolicy);
    return this; // chainable
  }

  /**
   * Sets the ordering to be imposed upon existing <code>Bin</code>s. The
   * method {@link #packAll} will sort bins by each bin's <code>compareTo</code>
   * method, preferably upon the bin's remaining capacity, with this policy.
   * A suggested default would be
   * {@link net.jnellis.binpack.preorder.DescendingPolicy}.
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   */
  public BinPacker<T>
  setExistingBinPreOrderPolicy(PreOrderPolicy<Bin<T>> preOrderPolicy) {
    this.existingBinPreOrderPolicy = Optional.ofNullable(preOrderPolicy);
    return this; // chainable
  }

  /**
   * Sets the ordering to be imposed upon available capacities of new
   * <code>Bin</code>s. The method {@link #packAll} will sort bin capacities
   * in the order they will be tried when trying to fit a piece in a new
   * bin. A suggested default would be the
   * {@link net.jnellis.binpack.preorder.AscendingPolicy}
   * @param preOrderPolicy The pre-ordering algorithm
   * @return Returns this BinPacker for use in chainable operations.
   */
  public BinPacker<T>
  setAvailableCapacitiesPreOrderPolicy(PreOrderPolicy<T> preOrderPolicy) {
    this.availableCapacitiesPreOrderPolicy = Optional.ofNullable
        (preOrderPolicy);
    return this; // chainable
  }

  /**
   * Sets the packing algorithm to be used to fit pieces into bins.
   * @param packingPolicy The algorithm to pack one piece into a set of bins.
   * @return Returns this BinPacker for use in chainable operations.
   */
  abstract public BinPacker<T> setPackingPolicy(PackingPolicy<T> packingPolicy);


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
      // reorder inputs
      pieces = preOrderPolicy.orElse(new AsIsPolicy<>()).order(pieces);

      existingBins = existingBinPreOrderPolicy.orElse(new AsIsPolicy<>())
          .order(existingBins);

      availableCapacities = availableCapacitiesPreOrderPolicy
          .orElse(new AsIsPolicy<>()).order(availableCapacities);

      // pack one at a time
      for(T piece : pieces){
        existingBins = this.pack( piece, existingBins, availableCapacities);
      }
      return existingBins;

    } else {
      throw new NoSuchElementException("No PackingPolicy found for " +
          this.getClass().getName() + ".  Use this.setPackingPolicy()");
    }
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
  @Override
  public List<Bin<T>> pack(T piece,
                           List<Bin<T>> existingBins,
                           List<T> availableCapacities) {
    return packingPolicy.get()
        .pack(piece, existingBins, availableCapacities);
  }
}
