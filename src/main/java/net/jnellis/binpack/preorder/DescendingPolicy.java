/*
 * DescendingPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.preorder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * In place sorting of pieces in their descending or reverse 'natural' ordering.
 */
public class DescendingPolicy<T extends Comparable<T>> implements
    PreOrderPolicy<T> {

  @Override
  public List<T> order(List<T> pieces) {
    Collections.sort(pieces, Comparator.<T>reverseOrder());
    return pieces;
  }

}
