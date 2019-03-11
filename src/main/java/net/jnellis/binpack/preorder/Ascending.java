/*
 * Ascending.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.preorder;

import java.util.Collections;
import java.util.List;


/**
 * In place sorting of pieces in their ascending or 'natural' ordering.
 */
public class Ascending<T extends Comparable<? super T>>
    implements PreOrderPolicy<T> {

  @Override
  public List<T> order(final List<T> pieces) {

    Collections.sort(pieces);
    return pieces;
  }
}
