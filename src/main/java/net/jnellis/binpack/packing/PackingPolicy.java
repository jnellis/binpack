/*
 * PackingPolicy.java
 *
 * Copyright (c) 2015. Joe Nellis
 * Distributed under MIT License. See accompanying file License.txt or at
 * http://opensource.org/licenses/MIT
 */

package net.jnellis.binpack.packing;

import net.jnellis.binpack.Bin;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A packing algorithm that attempts to find a bin that can fit a piece from a
 * list of bins.
 */
@FunctionalInterface
public interface PackingPolicy<T extends Comparable<T>> {

  /**
   * Creates a stream that traverses the given list in reverse order. The
   * given list, {@code bins}, should extend {@link java.util.RandomAccess} for
   * best performance.
   *
   * @param bins List of existing bins.
   * @param <B>  meant to be a bin type but can be any object.
   * @return A stream that traverses the given list in reverse order.
   */
  static <B> Stream<B> reverseStream(List<B> bins) {

    final int lastIndex = bins.size() - 1;
    return IntStream.rangeClosed(0, lastIndex)
                    .mapToObj(i -> bins.get(lastIndex - i));
  }

  /**
   * Chooses a bin that will fit the piece from a list of bins.
   *
   * @param piece        The piece to be fitted into an existing bin.
   * @param existingBins List of existing bins where the piece could fit.
   * @return Returns an Optional bin that represents the bin it found, or not.
   */
  Optional<Bin<T>> chooseBin(T piece, List<Bin<T>> existingBins);

  /**
   * Creates a predicate for bins that can fit this particular piece.
   * <p>
   * Usage:
   * <pre>
   *   collection.stream()
   *             .filter(binsThatCanFit(piece);
   * </pre>
   *
   * @param piece The piece that will be packed next.
   * @return Predicate for checking if a bin fits the given piece.
   */
  default Predicate<Bin<T>> binsThatCanFit(final T piece) {

    return bin -> bin.canFit(piece);
  }
}