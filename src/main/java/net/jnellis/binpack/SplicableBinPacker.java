package net.jnellis.binpack;

import net.jnellis.binpack.packing.PackingPolicy;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.lang.Math.*;

/**
 * User: Joe
 * Date: 12/1/2014
 * Time: 6:27 PM
 */
public class SplicableBinPacker extends BinPacker<Double> {


  @Override
  public BinPacker<Double> setPackingPolicy(PackingPolicy<Double> packingPolicy) {
    this.packingPolicy = Optional.of(packingPolicy);
    return null;
  }

  @Override
  public Stream<Bin<Double>> pack(Stream<Double> pieces,
                                  Stream<Bin<Double>> existingBins,
                                  Stream<Double> availableCapacities) {

    return super.pack(createSplicePieces(pieces, availableCapacities),
        existingBins,
        availableCapacities);
  }

  /**
   * Creates a stream of pieces that represents an originally larger piece.
   *
   * @param divisions
   * @param max
   * @param remainder
   * @return
   */
  private DoubleStream createDoubleStream(int divisions, double max, double
      remainder) {
    int numSplicePieces = remainder > 0 ? divisions + 1 : divisions;
    double splices[] = new double[numSplicePieces];

    Arrays.fill(splices, 0, divisions, max);
    if (remainder > 0) {
      splices[divisions] = remainder;
    }
    return DoubleStream.of(splices);
  }

  /**
   * Finds any pieces that are longer than the maximum available capacity
   * and breaks them up into smaller pieces of max available capacity
   * and a remainder piece.
   *
   * @param pieces     Stream of Doubles.
   * @param capacities Stream of available capacities.
   * @return Returns the new <i>pieces</i> stream.
   */
  public Stream<Double> createSplicePieces(Stream<Double> pieces,
                                           Stream<Double> capacities) {
    //@todo: remove stream assignments. yo more flo
    Stream<Double> stream = pieces;
    OptionalDouble maxAvailableCapacity = capacities.mapToDouble(i -> i).max();

    if (maxAvailableCapacity.isPresent()) {
      double max = maxAvailableCapacity.getAsDouble();
      // copy this stream and break up long piece by the max available
      // capacity.
      stream = pieces.flatMapToDouble(piece -> {
            DoubleStream result = DoubleStream.empty();
            if (piece > max) {
              // compute num of divisors, the remainder.
              int divisions = toIntExact(round(floor(piece / max)));
              double remainder = piece - divisions * max;

              // fill an array with the max values and one remainder
              result = createDoubleStream(divisions, max, remainder);
            } else {
              result = DoubleStream.of(piece);
            }
            return result;
          }
      ).boxed();
    }
    return pieces;
  }


}