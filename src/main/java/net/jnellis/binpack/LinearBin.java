/*
 * LinearBin.java
 *
 * Created on August 18, 2006, 6:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A bin for storing Double values.
 *
 * @author Joe Nellis
 */
public class LinearBin implements Bin<Double> {

  private List<Double> pieces = new ArrayList<>();
  private final List<Double> capacities;

  /**
   * Flag indicating whether this bin was an existing bin.
   */
  private final boolean existing;

  public LinearBin(@NotNull List<Double> capacities) {
    this.capacities = capacities;
    this.existing = false;
  }

  public LinearBin(@NotNull List<Double> capacities, boolean existing) {
    this.capacities = capacities;
    this.existing = existing;
  }

  @Override
  public boolean add(Double piece) {
    synchronized (pieces) {
      if (this.canFit(piece)) {
        return pieces.add(piece);
      }
      return false;
    }
  }

  /**
   * Searches through the order of available capacities in the given order until
   * it finds a capacity that fits.
   *
   * @param piece Piece to be fitted.
   * @return returns true if this piece can be fitted.
   */
  @Override
  public boolean canFit(Double piece) {
      Double newTotal = getTotal() + piece;
      return capacities.parallelStream()
          .anyMatch(capacity -> capacity >= newTotal);
  }

  @Override
  public final boolean isExisting() {
    return existing;
  }

  @Override
  public final Double getTotal() {
    return pieces.parallelStream().reduce(Double::sum).orElse(0.0);
  }                                                          //|\\  mr mustache

  @Override
  public final List<Double> getCapacities() {
    return Collections.unmodifiableList(capacities);
  }

  @Override
  public final List<Double> getPieces() {
    return Collections.unmodifiableList(pieces);
  }

  public static double getMaxRemainingCapacity(Bin<Double> bin) {
    double maxCapacity = bin.getCapacities().parallelStream()
        .reduce(Double::max).orElse(0.0);
                                   //|\\  strikes again
    return maxCapacity - bin.getTotal();
  }

  @Override
  public int compareTo(@NotNull Bin<Double> otherBin) {

    return Double.compare(getMaxRemainingCapacity(this),
        getMaxRemainingCapacity(otherBin));

  }
}
