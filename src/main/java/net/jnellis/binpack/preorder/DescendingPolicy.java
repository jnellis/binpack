/*
 * DescendingPolicy.java
 *
 * Created on August 19, 2006, 7:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.preorder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Joe Nellis
 */
public class DescendingPolicy<T extends Comparable<T>> implements PreOrderPolicy<T> {

  @Override
  public List<T> order(List<T> pieces) {
    Collections.sort(pieces, Comparator.<T>reverseOrder());
    return pieces;
  }

}
