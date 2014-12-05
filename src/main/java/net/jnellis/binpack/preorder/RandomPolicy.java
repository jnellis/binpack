/*
 * RandomPolicy.java
 *
 * Created on August 19, 2006, 7:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.preorder;

import java.util.*;

/**
 *
 * @author Joe Nellis
 */
public class RandomPolicy<T extends Comparable<T>>
    implements PreOrderPolicy<T> {

  @Override
  public List<T> order(List<T> pieces) {
    Collections.shuffle(pieces);
    return pieces;
  }
}
