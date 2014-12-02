/*
 * AsIsPolicy.java
 *
 * Created on August 26, 2006, 11:04 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.preorder;

import java.util.stream.Stream;

/**
 * @author Joe Nellis
 */
public class AsIsPolicy<T extends Comparable<T>> implements PreOrderPolicy<T> {
  @Override
  public Stream<T> order(Stream<T> pieces) {
    return pieces;
  }
}
