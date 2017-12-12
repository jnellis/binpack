/*
 * RandomPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.preorder;

import java.util.Collections;
import java.util.List;

/**
 * In place random ordering of pieces.
 */
public class RandomPolicy<T extends Comparable<T>>
    implements PreOrderPolicy<T> {

  @Override
  public List<T> order(final List<T> pieces) {
    Collections.shuffle(pieces);
    return pieces;
  }
}
