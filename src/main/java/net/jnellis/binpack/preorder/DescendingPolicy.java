/*
 * DescendingPolicy.java
 *
 * Created on August 19, 2006, 7:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.preorder;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * @author Joe Nellis
 */
public class DescendingPolicy<T extends Comparable<T>> implements PreOrderPolicy<T> {

  @Override
  public Stream<T> order(Stream<T> pieces) {
    return pieces.sorted(Comparator.reverseOrder());
  }

}
