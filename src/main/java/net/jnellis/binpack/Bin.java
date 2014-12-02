package net.jnellis.binpack;

import java.util.List;

/**
 * User: Joe
 * Date: 12/1/2014
 * Time: 11:37 AM
 */
public interface Bin<T> {
  boolean add(T d);

  boolean canFit(T piece);

  boolean isExisting();

  T getTotal();

  List<T> getCapacities();

  List<T> getPieces();
}
