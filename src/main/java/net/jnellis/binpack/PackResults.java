/*
 * PackResults.java
 *
 * Created on August 13, 2006, 4:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author Joe Nellis
 */
public class PackResults {
  private ArrayList<Bin> bins = new ArrayList<>();
  private double totalWaste = 0.0;
  private double total = 0.0;
  private boolean dirty = true;
  private int boards = 0;
  private double medianWaste = 0.0; // Waste of the middle board

  /**
   * Creates a new instance of PackResults
   */
  public PackResults() {
  }

  public double getMedianWaste() {
    if (bins.isEmpty()) {
      return 0.0;
    }
    ArrayList<Double> wastes = new ArrayList<>(getBoards());
    wastes.addAll(bins.stream().map(BinImpl::getWaste).collect(Collectors
        .toList()));
    Collections.sort(wastes);
    int size = wastes.size();
    if (wastes.size() % 2 == 0) {
      return (wastes.get(size / 2) +
          wastes.get(size / 2 - 1)) / 2;
    }
    return wastes.get(size / 2);
  }

  public void clear() {
    bins = new ArrayList<>();
    totalWaste = 0.0;
    total = 0.0;
    dirty = true;
  }

  public ArrayList<Bin> getBins() {
    return bins;
  }

  public void setBins(ArrayList<Bin> bins) {
    dirty = true;
    this.bins = bins;
  }

  public void addBin(Bin bin) {
    dirty = true;
    bins.add(bin);
  }

  public double getTotal() {
    if (dirty) {
      getTotalWaste();
    }
    return total;

  }

  public double getTotalWaste() {
    if (dirty) {
      total = bins.get(0).getMax() * bins.size();
      totalWaste = 0;
      for (Bin b : bins) {
        totalWaste += b.getWaste();
      }
      dirty = false;
    }
    return totalWaste;
  }

  public int getBoards() {
    return bins.size();
  }

}
