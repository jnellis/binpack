/*
 * PreOrderPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.preorder;

import java.util.List;

/**
 * An ordering policy for sorting pieces before being packed.
 */
@FunctionalInterface
public interface PreOrderPolicy<T extends Comparable<? super T>> {

  /**
   * Orders pieces and returns the ordered list.
   *
   * @param pieces A list of comparable pieces.
   * @return the modified list of pieces.
   */
  List<T> order(List<T> pieces);
}

