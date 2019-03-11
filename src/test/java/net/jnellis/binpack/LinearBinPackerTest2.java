package net.jnellis.binpack;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Joe Nellis
 * Date: 3/9/2019
 * Time: 11:08 AM
 */
public class LinearBinPackerTest2 {

  LinearBinPacker binPacker = new LinearBinPacker();

  List<Double> pieces = Arrays.asList(8d, 7.9d, 6d, 5d, 3d, 1.9d, 0.1d);

  List<LinearBin> bins = new ArrayList<>();

  List<Double> capacities = Arrays.asList(3d, 4d, 8d);

  @Test
  public void packAll() {

    Double[][] expected = new Double[][]{
        {8d}, {7.9d,0.1d}, {6d, 1.9d}, {5d, 3d}};


    List<LinearBin> packedBins = binPacker.packAll(pieces, bins, capacities);
    for (int i = 0; i < expected.length; i++) {
      Object[] binResults =  packedBins.get(i).getPieces().toArray();
      Assert.assertArrayEquals(expected[i], binResults  );
    } 
  }
}