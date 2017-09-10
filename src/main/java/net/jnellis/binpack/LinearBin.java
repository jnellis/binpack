/*
 * LinearBin.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */
package net.jnellis.binpack;


import java.util.*;


/**
 * A packing bin for storing Double values.
 *
 * @author Joe Nellis
 */
public class LinearBin implements Bin<Double> {

  private final List<Double> pieces = new ArrayList<>();
  private final List<Double> capacities;

  private final Double maxCapacity;

  /**
   * Flag indicating whether this bin was an existing bin.
   */
  private final boolean existing;
  private Double total = 0.0;

  /**
   * Create a new bin. It will not be marked as an existing bin.
   *
   * @param capacities The list of capacities that this bin could have.
   */
  public LinearBin(final List<Double> capacities) {
    assert capacities.size() > 0;
    this.capacities = new ArrayList<>(capacities);
    this.existing = false;
    this.maxCapacity = Collections.max(capacities);
  }

  /**
   * Creates a bin that represents an existing bin. An existing bin has
   * a single capacity.
   *
   * @param capacity The single capacity of this bin.
   */
  public LinearBin(final Double capacity) {
    this.capacities = new ArrayList<>();
    this.capacities.add(capacity);
    this.existing = true;
    this.maxCapacity = capacity;
  }

  /**
   * Automatically calls {@link #canFit} before placing the piece in the bin.
   *
   * @param piece The piece to add.
   * @return true if the piece was added.
   */
  @Override
  public synchronized boolean add(final Double piece) {
    if (piece < 0.0) {
      throw new AssertionError("Negative value pieces not allowed: " + piece);
    }
    if (this.canFit(piece) && pieces.add(piece)) {
      total += piece;
      return true;
    }
    return false;
  }

  /**
   * Computes the remaining capacity of this bin based on the maximum
   * of its potential capacities.
   *
   * @return The maximum potential remaining capacity.
   */
  @Override
  public Double getMaxRemainingCapacity() {

    return this.maxCapacity - getTotal();
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

    final Double newTotal = getTotal() + piece;
    return capacities.stream().anyMatch(capacity -> capacity >= newTotal);
  }

  /**
   * Finds the minimal capacity needed given the current total.
   *
   * @return The minimal capacity of this bins capacities that is still
   * bigger than the total packed.
   */
  public Double getSmallestCapacityNeeded() {

    final Optional<Double> min = getCapacities()
        .stream()
        .filter(capacity -> capacity >= getTotal())
        .min(Comparator.comparing(capacity -> capacity - getTotal()));
    return min.orElseThrow(Bin::mustBeAtLeastOneCapacityException);
  }

  @Override
  public final boolean isExisting() {
    return existing;
  }

  @Override
  public final Double getTotal() {
    return total;
  }

  @Override
  public final List<Double> getCapacities() {
    return Collections.unmodifiableList(capacities);
  }

  @Override
  public final List<Double> getPieces() {
    return Collections.unmodifiableList(pieces);
  }


  @Override
  public String toString() {
    return "LinearBin{" + "pieces=" + pieces + ", capacities=" + capacities +
        ", total=" + total + ", existing=" + existing + '}';
  }
}
