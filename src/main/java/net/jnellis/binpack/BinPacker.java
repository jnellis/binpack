/*
 * BinPacker.java
 *
 * Created on August 5, 2006, 10:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack;

import net.jnellis.binpack.packing.PackingPolicy;
import net.jnellis.binpack.preorder.AsIsPolicy;
import net.jnellis.binpack.preorder.PreOrderPolicy;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Joe Nellis
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

  public BinPacker<T> setPreOrderPolicy(PreOrderPolicy<T> preOrderPolicy) {
    this.preOrderPolicy = Optional.ofNullable(preOrderPolicy);
    return this; // chainable
  }

  public BinPacker<T>
  setExistingBinPreOrderPolicy(PreOrderPolicy<Bin<T>> preOrderPolicy) {
    this.existingBinPreOrderPolicy = Optional.ofNullable(preOrderPolicy);
    return this; // chainable
  }

  public BinPacker<T>
  setAvailableCapacitiesPreOrderPolicy(PreOrderPolicy<T> preOrderPolicy) {
    this.availableCapacitiesPreOrderPolicy = Optional.ofNullable
        (preOrderPolicy);
    return this; // chainable
  }

  abstract public BinPacker<T> setPackingPolicy(PackingPolicy<T> packingPolicy);


  /**
   * Attempts to store <i>pieces</i> into the least amount of bins, starting
   * with <i>existingMaterial</i>. When a new bin is required, one is copied
   * from the <i>availableMaterial</i> set according to the
   * <i>availableMaterialPreOrderPolicy</i>.
   *
   * @param pieces              List of pieces to be packed.
   * @param availableCapacities An array of bin capacities we can make when we
   *                            need another bin.
   * @param existingBins        The initial set of bins will be made with these
   *                            capacities.
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
