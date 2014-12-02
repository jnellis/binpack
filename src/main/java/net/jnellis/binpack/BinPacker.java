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
import net.jnellis.binpack.preorder.DescendingPolicy;
import net.jnellis.binpack.preorder.PreOrderPolicy;

import java.util.stream.Stream;

/**
 * @author Joe Nellis
 */
abstract public class BinPacker<T extends Comparable<T>> {

  /**
   * The ordering imposed upon the inputs (i.e. ascending, descending, etc,)
   */
  PreOrderPolicy<T> preOrderPolicy;

  /**
   * The packing algorithm to use.
   */
  PackingPolicy<T> packingPolicy;

  /**
   * Fixed bin sizes that need to be filled first need an ordering policy also.
   */
  PreOrderPolicy<T> existingMaterialPreOrderPolicy;

  /**
   * An ordering policy for available bin sizes (not existing bins) for when
   * a piece needs a new bin.
   */
  PreOrderPolicy<T> availableMaterialPreOrderPolicy;

  public BinPacker setPreOrderPolicy(PreOrderPolicy<T>
                                         preOrderPolicy) {
    this.preOrderPolicy = preOrderPolicy;
    return this; // chainable
  }

  abstract public BinPacker setPackingPolicy(PackingPolicy<T> packingPolicy);

  public BinPacker setExistingMaterialPreOrderPolicy(
      PreOrderPolicy<T> existingMaterialPreOrderPolicy
  ) {
    this.existingMaterialPreOrderPolicy = existingMaterialPreOrderPolicy;
    return this; // chainable
  }

  public BinPacker setAvailableMaterialPreOrderPolicy(
      PreOrderPolicy<T> availableMaterialPreOrderPolicy
  ) {
    this.availableMaterialPreOrderPolicy = availableMaterialPreOrderPolicy;
    return this; // chainable
  }

  public BinPacker() {
    // some defaults
    this.preOrderPolicy = new DescendingPolicy<T>();
    //this.packingPolicy = new BestFitPackingPolicy<>();
    this.existingMaterialPreOrderPolicy = new DescendingPolicy<T>();
    this.availableMaterialPreOrderPolicy = new DescendingPolicy<T>();
  }

  /**
   * Attempts to store <i>pieces</i> into the least amount of bins, starting
   * with <i>existingMaterial</i>. When a new bin is required, one is copied
   * from the <i>availableMaterial</i> set according to the
   * <i>availableMaterialPreOrderPolicy</i>.
   * <p>
   * <code>allowSplices</code> being true will cause
   * some pre-processing of the pieces. It will replace any piece longer than
   * the maximum available capacity is divided into maximum capacity sized
   * pieces with a remainder piece.
   *
   * @param pieces              List of pieces to be packed.
   * @param availableCapacities An array of bin capacities we can make when we
   *                            need another bin.
   * @param existingCapacities  The initial set of bins will be made with these
   *                            capacities.
   */
  public Stream<Bin<T>> pack(Stream<T> pieces,
                             Stream<T> availableCapacities,
                             Stream<T> existingCapacities) {

    availableMaterialPreOrderPolicy.order(availableCapacities);
    existingMaterialPreOrderPolicy.order(existingCapacities);
    preOrderPolicy.order(pieces);
    return packingPolicy.pack(pieces, availableCapacities, existingCapacities);
  }

}
