/*
 * PreOrderPolicy.java
 *
 * Created on August 19, 2006, 7:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack.preorder;


import java.util.stream.Stream;

/**
 * @author Joe Nellis
 */
@FunctionalInterface
public interface PreOrderPolicy<T extends Comparable<T>> {
   public Stream<T> order(Stream<T> pieces);
}

