/*
 * AsIsPolicy.java
 *
 * Copyright (c) 2014. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.preorder;

import java.util.List;

/**
 * A no-op ordering that leaves pieces as is.
 */
public class AsIsPolicy<T extends Comparable<T>> implements PreOrderPolicy<T> {
  @Override
  public List<T> order(final List<T> pieces) {
    return pieces;
  }
}
