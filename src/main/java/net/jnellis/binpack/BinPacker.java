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

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

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
      existingMaterialPreOrderPolicy = Optional.empty();

  /**
   * An ordering policy for available bin sizes (not existing bins) for when
   * a piece needs a new bin.
   */
  private Optional<PreOrderPolicy<T>>
      availableMaterialPreOrderPolicy = Optional.empty();

  public BinPacker<T> setPreOrderPolicy(PreOrderPolicy<T> preOrderPolicy) {
    this.preOrderPolicy = Optional.ofNullable(preOrderPolicy);
    return this; // chainable
  }


  public BinPacker<T>
  setExistingMaterialPreOrderPolicy(PreOrderPolicy<Bin<T>> preOrderPolicy) {
    this.existingMaterialPreOrderPolicy = Optional.ofNullable(preOrderPolicy);
    return this; // chainable
  }

  public BinPacker<T>
  setAvailableMaterialPreOrderPolicy(PreOrderPolicy<T> preOrderPolicy) {
    this.availableMaterialPreOrderPolicy = Optional.ofNullable(preOrderPolicy);
    return this; // chainable
  }

  abstract public BinPacker<T> setPackingPolicy(PackingPolicy<T> packingPolicy);

  public BinPacker() {
    // some defaults
    this.preOrderPolicy = Optional.of(new AsIsPolicy<>());
    this.existingMaterialPreOrderPolicy = Optional.of(new AsIsPolicy<>());
    this.availableMaterialPreOrderPolicy = Optional.of(new AsIsPolicy<>());
    //this.packingPolicy = new BestFitPackingPolicy<>();
  }

  /**
   * Attempts to store <i>pieces</i> into the least amount of bins, starting
   * with <i>existingMaterial</i>. When a new bin is required, one is copied
   * from the <i>availableMaterial</i> set according to the
   * <i>availableMaterialPreOrderPolicy</i>.
   *
   * @param pieces              List of pieces to be packed.
   * @param availableCapacities An array of bin capacities we can make when we
   *                            need another bin.
   * @param existingBins  The initial set of bins will be made with these
   *                            capacities.
   */
  public Stream<Bin<T>> pack(Stream<T> pieces,
                             Stream<Bin<T>> existingBins,
                             Stream<T> availableCapacities) {

    if (availableMaterialPreOrderPolicy.isPresent()) {
      availableMaterialPreOrderPolicy.get().order(availableCapacities);
    }
    if (existingMaterialPreOrderPolicy.isPresent()) {
      existingMaterialPreOrderPolicy.get().order(existingBins);
    }
    if (preOrderPolicy.isPresent()) {
      preOrderPolicy.get().order(pieces);
    }
    if (packingPolicy.isPresent()) {
      return packingPolicy.get().pack(pieces, existingBins,
          availableCapacities);
    } else {
      throw new NoSuchElementException("No PackingPolicy found for " +
          this.getClass().getName() + ".  Use this.setPackingPolicy()");
    }
  }


}
