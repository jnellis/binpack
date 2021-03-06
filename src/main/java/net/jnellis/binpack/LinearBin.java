/*
 * LinearBin.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */
package net.jnellis.binpack;


import java.util.*;
import java.util.function.Supplier;


/**
 * A packing bin for storing Double values.
 *
 * @author Joe Nellis
 */
public class LinearBin implements Bin<Double, Double> {

  private final List<Double> pieces = new ArrayList<>();

  private final List<Double> capacities;

  private final double maxCapacity;

  /**
   * Flag indicating whether this bin was an existing bin.
   */
  private final boolean existing;

  private double total = 0.0;

  /**
   * Create a new bin, it will not be marked as an existing bin.
   *
   * @param capacities The list of capacities that this bin could have.
   */
  public LinearBin(final List<Double> capacities) {

    this.capacities = new ArrayList<>(Objects.requireNonNull(capacities));
    if (this.capacities.isEmpty()) {
      throw CapacitySupport.mustBeAtLeastOneCapacityException();
    }
    this.existing = false;
    this.maxCapacity = Collections.max(this.capacities);
  }

  /**
   * Creates a bin that represents an existing bin. An existing bin has a single
   * capacity.
   *
   * @param capacity The single capacity of this bin.
   */
  public LinearBin(final Double capacity) {

    this.capacities = Collections.singletonList(capacity);
    this.existing = true;
    this.maxCapacity = capacity;
  }

  /**
   * Generates a Supplier that returns a new LinearBin when {@link Supplier#get}
   * is called.
   *
   * @param capacities List of available capacities for the bins created by this
   *                   lambda.
   * @return Supplier of LinearBin.
   */
  public static Supplier<LinearBin> newBinSupplier(final List<Double> capacities) {

    return () -> new LinearBin(capacities);
  }

  /**
   * Automatically calls {@link #canFit} before placing the piece in the bin.
   *
   * @param piece The piece to add.
   * @return true if the piece was added.
   */
  @Override
  public boolean add(final Double piece) {

    if (piece < 0.0) {
      throw new AssertionError("Negative value pieces not allowed: " + piece);
    }
    if (this.canFit(piece)) {
      pieces.add(piece);
      total += piece;
      return true;
    }
    return false;
  }

  /**
   * Searches through the order of available capacities in the given order until
   * it finds a capacity that fits.
   *
   * @param piece Piece to be fitted.
   * @return returns true if this piece can be fitted.
   */
  @Override
  public boolean canFit(final Double piece) {

    return capacities.stream()
                     .anyMatch(capacity -> capacity >= total + piece);
  }

  @Override
  public final boolean isExisting() {

    return existing;
  }

  @Override
  public final List<Double> getPieces() {

    return Collections.unmodifiableList(pieces);
  }

  @Override
  public final Double getTotal() {

    return total;
  }

  @Override
  public final List<Double> getCapacities() {

    return Collections.unmodifiableList(capacities);
  }

  /**
   * Computes the remaining capacity of this bin based on the maximum of its
   * potential capacities.
   *
   * @return The maximum potential remaining capacity.
   */
  @Override
  public Double getMaxRemainingCapacity() {

    return this.maxCapacity - total;
  }

  /**
   * Finds the minimal capacity needed given the current total.
   *
   * @return The minimal capacity of this bins capacities that is still bigger
   * than the total packed.
   */
  @Override
  public Double getSmallestCapacityNeeded() {

    final Optional<Double> min = this.capacities
        .stream()
        .filter(capacity -> capacity >= total)
        .min(Comparator.comparing(capacity -> capacity - total));
    return min.orElseThrow(CapacitySupport::mustBeAtLeastOneCapacityException);
  }

  /**
   * Determines if this piece is larger than half the maximum capacity.
   * @param piece a piece 
   * @return true if piece is more than half max capacity.
   */
  public boolean isMoreThanHalfMaxCapacity(final Double piece) {

    return maxCapacity / 2 < piece;
  }

  @Override
  public String toString() {

    return "LinearBin{" + "pieces=" + pieces + ", capacities=" + capacities +
        ", total=" + total + ", existing=" + existing + '}';
  }


}
