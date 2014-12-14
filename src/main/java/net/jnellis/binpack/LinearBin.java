/*
 * LinearBin.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */
package net.jnellis.binpack;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/**
 * A packing bin for storing Double values.
 *
 * @author Joe Nellis
 */
public class LinearBin implements Bin<Double> {

  private final List<Double> pieces = new ArrayList<>();
  private final List<Double> capacities;
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
  public LinearBin(@NotNull List<Double> capacities) {
    assert capacities.size() > 0;
    this.capacities = new ArrayList<>(capacities);
    this.existing = false;
  }

  /**
   * Creates a bin that represents an existing bin. An existing bin has
   * a single capacity.
   *
   * @param capacity The single capacity of this bin.
   */
  public LinearBin(@NotNull Double capacity) {
    this.capacities = new ArrayList<>();
    this.capacities.add(capacity);
    this.existing = true;
  }

  /**
   * Computes the remaining capacity of this bin based on the maximum
   * of its potential capacities.
   *
   * @param bin The bin we want to compute remaining capacity.
   * @return The maximum potential remaining capacity.
   */
  public static double getMaxRemainingCapacity(@NotNull Bin<Double> bin) {

    Optional<Double> max = bin.getCapacities().stream().max(Double::compare);
    return max.get() - bin.getTotal();
  }

  /**
   * Automatically calls {@link #canFit} before placing the piece in the bin.
   *
   * @param piece The piece to add.
   * @return true if the piece was added.
   */
  @Override
  public synchronized boolean add(Double piece) {
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
   * Searches through the order of available capacities in the given order until
   * it finds a capacity that fits.
   *
   * @param piece Piece to be fitted.
   * @return returns true if this piece can be fitted.
   */
  @Override
  public boolean canFit(@NotNull Double piece) {
    Double newTotal = getTotal() + piece;
    return capacities.stream().anyMatch(capacity -> capacity >= newTotal);
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

  /**
   * Compares two bins based on their maximum potential remaining capacity.
   *
   * @param otherBin the other bin to compare.
   * @return Returns the same condition as {@link Double#compareTo}
   */
  @Override
  public int compareTo(@NotNull Bin<Double> otherBin) {
    return Double.compare(getMaxRemainingCapacity(this),
        getMaxRemainingCapacity(otherBin));

  }

  @Override
  public String toString() {
    return "LinearBin{" + "pieces=" + pieces + ", capacities=" + capacities +
        ", total=" + total + ", existing=" + existing + '}';
  }
}
