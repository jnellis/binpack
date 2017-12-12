package net.jnellis.binpack.packing;

import net.jnellis.binpack.LinearBin;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static net.jnellis.binpack.collectors.BinPackCollector.bestFitPacking;

/**
 * User: Joe Nellis
 * Date: 12/5/2017
 * Time: 9:04 PM
 */
public class BinPackCollectorJavadocTest {

  @Test
  public void testJavadocCode() {

    Random random = new Random(42);
    List<Double> boardLengths = random.doubles(100, 0, 16)
                                      .boxed()
                                      .collect(toList());

    List<Double> stockLengths = Arrays.asList(8d, 12d, 16d);


    Collection<LinearBin> bins =
        boardLengths.stream()
                    .collect(bestFitPacking(
                        () -> new LinearBin(stockLengths),
                        Function.identity()));
    assert bins.size() == 56;
    assert ("[LinearBin{pieces=[14.453956234754852, 1.543214654093184], " +
        "capacities=[8.0, 12.0, 16.0], total=15.997170888848036, " +
        "existing=false}, LinearBin{pieces=[13.127869174181546, " +
        "2.868375053998678], capacities=[8.0, 12.0, 16.0], " +
        "total=15.996244228180224, existing=false}, " +
        "LinearBin{pieces=[9.581392594320283, 5.9813782979286785, " +
        "0.4324778701141341], capacities=[8.0, 12.0, 16.0], " +
        "total=15.995248762363095, existing=false}, " +
        "LinearBin{pieces=[13.215453950205136, 2.755487003005639], " +
        "capacities=[8.0, 12.0, 16.0], total=15.970940953210775, " +
        "existing=false}, LinearBin{pieces=[13.949033535437419, " +
        "2.0201251727802454], capacities=[8.0, 12.0, 16.0], " +
        "total=15.969158708217664, existing=false}, " +
        "LinearBin{pieces=[9.280398152032971, 6.6830007480467], " +
        "capacities=[8.0, 12.0, 16.0], total=15.963398900079671, " +
        "existing=false}, LinearBin{pieces=[10.186312765700512, " +
        "5.7640780058581615], capacities=[8.0, 12.0, 16.0], " +
        "total=15.950390771558673, existing=false}, " +
        "LinearBin{pieces=[14.70924452589947, 1.233361806964032], " +
        "capacities=[8.0, 12.0, 16.0], total=15.942606332863502, " +
        "existing=false}, LinearBin{pieces=[11.99849890008716, " +
        "3.3562811018613132, 0.5837522670439945], capacities=[8.0, 12.0, " +
        "16.0], total=15.938532268992468, existing=false}, " +
        "LinearBin{pieces=[11.414500125171665, 4.4891518667649635], " +
        "capacities=[8.0, 12.0, 16.0], total=15.903651991936629, " +
        "existing=false}, LinearBin{pieces=[7.418457212946453, " +
        "6.983855590772585, 1.487149071063289], capacities=[8.0, 12.0, 16.0]," +
        " total=15.889461874782327, existing=false}, " +
        "LinearBin{pieces=[10.931575548157527, 4.939511285322556], " +
        "capacities=[8.0, 12.0, 16.0], total=15.871086833480083, " +
        "existing=false}, LinearBin{pieces=[12.526428460640572, " +
        "2.8380556465500533, 0.5026918212252927], capacities=[8.0, 12.0, " +
        "16.0], total=15.867175928415918, existing=false}, " +
        "LinearBin{pieces=[9.85941896056286, 5.9897496974240685], " +
        "capacities=[8.0, 12.0, 16.0], total=15.849168657986928, " +
        "existing=false}, LinearBin{pieces=[13.084750893370229, " +
        "2.7337558061263607], capacities=[8.0, 12.0, 16.0], " +
        "total=15.81850669949659, existing=false}, " +
        "LinearBin{pieces=[7.414388336453522, 7.957230279614729, " +
        "0.43702666057545336], capacities=[8.0, 12.0, 16.0], " +
        "total=15.808645276643704, existing=false}, " +
        "LinearBin{pieces=[10.480854097757137, 5.317188454155977], " +
        "capacities=[8.0, 12.0, 16.0], total=15.798042551913113, " +
        "existing=false}, LinearBin{pieces=[6.185069989749579, " +
        "9.509598574234946], capacities=[8.0, 12.0, 16.0], " +
        "total=15.694668563984525, existing=false}, " +
        "LinearBin{pieces=[11.197738321074754, 4.4611568399802835], " +
        "capacities=[8.0, 12.0, 16.0], total=15.658895161055037, " +
        "existing=false}, LinearBin{pieces=[13.237158784239922, " +
        "2.4165048724601323], capacities=[8.0, 12.0, 16.0], " +
        "total=15.653663656700054, existing=false}, " +
        "LinearBin{pieces=[15.616551545894534], capacities=[8.0, 12.0, 16.0]," +
        " total=15.616551545894534, existing=false}, " +
        "LinearBin{pieces=[15.584570903934102], capacities=[8.0, 12.0, 16.0]," +
        " total=15.584570903934102, existing=false}, " +
        "LinearBin{pieces=[13.341859767106651, 2.2366335245897737], " +
        "capacities=[8.0, 12.0, 16.0], total=15.578493291696425, " +
        "existing=false}, LinearBin{pieces=[10.339711660762285, " +
        "5.042359541877085], capacities=[8.0, 12.0, 16.0], " +
        "total=15.38207120263937, existing=false}, " +
        "LinearBin{pieces=[15.197762154551466], capacities=[8.0, 12.0, 16.0]," +
        " total=15.197762154551466, existing=false}, " +
        "LinearBin{pieces=[12.040159177450416, 3.135427318770496], " +
        "capacities=[8.0, 12.0, 16.0], total=15.175586496220912, " +
        "existing=false}, LinearBin{pieces=[10.125916661652465, " +
        "5.045255200922767], capacities=[8.0, 12.0, 16.0], " +
        "total=15.171171862575232, existing=false}, " +
        "LinearBin{pieces=[12.020486508279362, 3.1383531501096247], " +
        "capacities=[8.0, 12.0, 16.0], total=15.158839658388986, " +
        "existing=false}, LinearBin{pieces=[9.39883810858073, " +
        "5.726718711634058], capacities=[8.0, 12.0, 16.0], " +
        "total=15.125556820214788, existing=false}, " +
        "LinearBin{pieces=[9.145924888479627, 5.936196634940174], " +
        "capacities=[8.0, 12.0, 16.0], total=15.0821215234198, " +
        "existing=false}, LinearBin{pieces=[4.433255841186186, " +
        "10.648783228713178], capacities=[8.0, 12.0, 16.0], " +
        "total=15.082039069899364, existing=false}, " +
        "LinearBin{pieces=[15.042759450966097], capacities=[8.0, 12.0, 16.0]," +
        " total=15.042759450966097, existing=false}, " +
        "LinearBin{pieces=[9.136645574637875, 5.905943903070359], " +
        "capacities=[8.0, 12.0, 16.0], total=15.042589477708233, " +
        "existing=false}, LinearBin{pieces=[5.90052661458089, " +
        "4.411969111067238, 4.666503958588866], capacities=[8.0, 12.0, 16.0]," +
        " total=14.978999684236994, existing=false}, " +
        "LinearBin{pieces=[8.63855094814991, 6.3322604827398745], " +
        "capacities=[8.0, 12.0, 16.0], total=14.970811430889784, " +
        "existing=false}, LinearBin{pieces=[10.046932406615563, " +
        "4.889266490679182], capacities=[8.0, 12.0, 16.0], " +
        "total=14.936198897294744, existing=false}, " +
        "LinearBin{pieces=[7.317073511116311, 7.563014734013686], " +
        "capacities=[8.0, 12.0, 16.0], total=14.880088245129997, " +
        "existing=false}, LinearBin{pieces=[7.5043603298490975, " +
        "7.364901962578578], capacities=[8.0, 12.0, 16.0], " +
        "total=14.869262292427676, existing=false}, " +
        "LinearBin{pieces=[7.68919226490295, 6.954577362249765], " +
        "capacities=[8.0, 12.0, 16.0], total=14.643769627152714, " +
        "existing=false}, LinearBin{pieces=[14.537833283321136], " +
        "capacities=[8.0, 12.0, 16.0], total=14.537833283321136, " +
        "existing=false}, LinearBin{pieces=[7.785450589219105, " +
        "6.735547306096459], capacities=[8.0, 12.0, 16.0], " +
        "total=14.520997895315563, existing=false}, " +
        "LinearBin{pieces=[14.387285275672923], capacities=[8.0, 12.0, 16.0]," +
        " total=14.387285275672923, existing=false}, " +
        "LinearBin{pieces=[14.304684733094541], capacities=[8.0, 12.0, 16.0]," +
        " total=14.304684733094541, existing=false}, " +
        "LinearBin{pieces=[6.900952882167378, 7.083775073379526], " +
        "capacities=[8.0, 12.0, 16.0], total=13.984727955546903, " +
        "existing=false}, LinearBin{pieces=[7.741501679268882, " +
        "6.153773502676662], capacities=[8.0, 12.0, 16.0], " +
        "total=13.895275181945545, existing=false}, " +
        "LinearBin{pieces=[13.850988741237636], capacities=[8.0, 12.0, 16.0]," +
        " total=13.850988741237636, existing=false}, " +
        "LinearBin{pieces=[12.94599706764383], capacities=[8.0, 12.0, 16.0], " +
        "total=12.94599706764383, existing=false}, " +
        "LinearBin{pieces=[12.891695082591967], capacities=[8.0, 12.0, 16.0]," +
        " total=12.891695082591967, existing=false}, " +
        "LinearBin{pieces=[12.886251518742117], capacities=[8.0, 12.0, 16.0]," +
        " total=12.886251518742117, existing=false}, " +
        "LinearBin{pieces=[12.40193113483481], capacities=[8.0, 12.0, 16.0], " +
        "total=12.40193113483481, existing=false}, " +
        "LinearBin{pieces=[12.327450204383055], capacities=[8.0, 12.0, 16.0]," +
        " total=12.327450204383055, existing=false}, " +
        "LinearBin{pieces=[11.922452899446169], capacities=[8.0, 12.0, 16.0]," +
        " total=11.922452899446169, existing=false}, " +
        "LinearBin{pieces=[11.64101888052589], capacities=[8.0, 12.0, 16.0], " +
        "total=11.64101888052589, existing=false}, " +
        "LinearBin{pieces=[11.155979668315672], capacities=[8.0, 12.0, 16.0]," +
        " total=11.155979668315672, existing=false}, " +
        "LinearBin{pieces=[10.161776231302209], capacities=[8.0, 12.0, 16.0]," +
        " total=10.161776231302209, existing=false}, " +
        "LinearBin{pieces=[8.773355067708025], capacities=[8.0, 12.0, 16.0], " +
        "total=8.773355067708025, existing=false}]")
        .equals(bins.toString());
  }
}