package net.jnellis.binpack.packing;

import net.jnellis.binpack.LinearBin;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static net.jnellis.binpack.LinearBin.newBinSupplier;
import static net.jnellis.binpack.collectors.BestFitPackingCollector.bestFitPacking;
import static org.junit.Assert.assertTrue;

/**
 * User: Joe Nellis
 * Date: 12/5/2017
 * Time: 9:04 PM
 */
public class BinPackCollectorJavadocTest {

  List<Double> boardLengths;

  List<Double> stockLengths;

  Collection<LinearBin> resultBins = null;

  @Before
  public void setup() {

    Random random = new Random(42);
    boardLengths = random.doubles(100, 0, 16)
                         .boxed()
                         .collect(toList());

    stockLengths = Arrays.asList(8d, 12d, 16d);

  }


  @Test
  public void testConvenienceMethod() {

    Collection<LinearBin> bins =
        boardLengths.stream()
                    .collect(bestFitPacking(
                        newBinSupplier(stockLengths),
                        Function.identity()));

    assertTrue(bins.size() == 56);
    if (resultBins != null) {
      assertTrue(bins.equals(resultBins));
    }
  }

  @Test
  public void testJavadocCode() {

    Collection<LinearBin> bins =
        boardLengths.stream()
                    .collect(bestFitPacking(
                        () -> new LinearBin(stockLengths),
                        Function.identity()));
    assertTrue(bins.size() == 56);
    if (resultBins != null) {
      assertTrue(bins.equals(resultBins));
    }
  }
}