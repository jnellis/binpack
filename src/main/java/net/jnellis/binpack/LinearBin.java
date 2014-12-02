/*
 * LinearBin.java
 *
 * Created on August 18, 2006, 6:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack;

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
  private double total = 0.0;
  private final List<Double> capacities;
  /**
   * Flag indicating whether this bin was an existing bin.
   */
  private final boolean existing;

  public LinearBin(List<Double> capacities){
    this.capacities = capacities;
    this.existing = false;
  }
  public LinearBin(List<Double> capacities, boolean existing) {
    this.capacities = capacities;
    this.existing = existing;
  }

  @Override
  public boolean add(Double piece) {
    if (!pieces.add(piece)) {
      return false;
    }
    total = total + piece;
    return true;
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
    double newTotal = getTotal() + piece;
    return
        getCapacities().stream().anyMatch(capacity -> capacity > newTotal);
  }

  @Override
  public boolean isExisting() {
    return existing;
  }

  @Override
  public Double getTotal() {
    return total;
  }

  @Override
  public List<Double> getCapacities() {
    return Collections.unmodifiableList(capacities);
  }

  @Override
  public List<Double> getPieces() {
    return Collections.unmodifiableList(pieces);
  }

}
