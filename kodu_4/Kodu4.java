/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö. Ülesanne nr 4
 * Teema: Paisktabelid
 *
 * Autor: Nikita Filin
 *
 *****************************************************************************/

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Kodu4 {

    /**
     * Genereerib isikukoodi lähtudes reeglitest püstitatud <a href=https://et.wikipedia.org/wiki/Isikukood>siin.</a>
     * <br>
     * Numbrite tähendused:
     * <ul style="list-style-type:none">
     *      <li> 1 - sugu ja sünniaasta esimesed kaks numbrit, (1...8) </li>
     *      <li> 2-3 - sünniaasta 3. ja 4. numbrid, (00...99) </li>
     *      <li> 4-5 - sünnikuu, (01...12) </li>
     *      <li> 6-7 - sünnikuupäev (01...31) </li>
     *      <li> 8-10 - järjekorranumber samal päeval sündinute eristamiseks (000...999) </li>
     *      <li> 11 - kontrollnumber (0...9) </li>
     * </ul>
     *
     * @return Eesti isikukoodi reeglitele vastav isikukood
     */
    static long genereeriIsikukood() {
        ThreadLocalRandom juhus = ThreadLocalRandom.current();
        LocalDate sünnikuupäev = LocalDate.ofEpochDay(juhus.nextLong(-62091, 84006)); // Suvaline kuupäeva a 1800-2199
        long kood = ((sünnikuupäev.getYear() - 1700) / 100 * 2 - juhus.nextInt(2)) * (long) Math.pow(10, 9) +
                sünnikuupäev.getYear() % 100 * (long) Math.pow(10, 7) +
                sünnikuupäev.getMonthValue() * (long) Math.pow(10, 5) +
                sünnikuupäev.getDayOfMonth() * (long) Math.pow(10, 3) +
                juhus.nextInt(1000);
        int korrutisteSumma = 0;
        int[] iAstmeKaalud = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        for (int i = 0; i < 10; i++) {
            korrutisteSumma += (int) (kood / (long) Math.pow(10, i) % 10 * iAstmeKaalud[9 - i]);
        }
        int kontroll = korrutisteSumma % 11;
        if (kontroll == 10) {
            int[] iiAstmeKaalud = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};
            korrutisteSumma = 0;
            for (int i = 0; i < 10; i++) {
                korrutisteSumma += (int) (kood / (long) Math.pow(10, i) % 10 * iiAstmeKaalud[9 - i]);
            }
            kontroll = korrutisteSumma % 11;
            kontroll = kontroll < 10 ? kontroll : 0;
        }
        return kood * 10 + kontroll;
    }


    /**
     * Abi meetod, mis tagastab isikukoodi sajandi
     * @param sajand isikukoodi sajandi number
     * @return vastav sajand
     */
    private static int sajand(int sajand) {
        if (sajand == 1 || sajand == 2) {
            return 18; // 1800-1899
        } else if (sajand == 3 || sajand == 4) {
            return 19; // 1900-1999
        } else if (sajand == 5 || sajand == 6) {
            return 20; // 2000-2099
        } else if (sajand == 7 || sajand == 8) {
            return 21; // 2100-2199
        } else {
            return -1;
        }
    }


    /**
     * Meetod sorteerib isikukoode vastavalt nõutele
     * @param jrj isikukoodide jäejend
     */
    public static void sort(long[] jrj) {
        // Loome 400 kimbu iga aasta jaoks + 1
        List<ArrayList<Long>> kimbud = new ArrayList<>(400);

        for (int i = 0; i < 400; i++) {
            kimbud.add(new ArrayList<>());
        }

        //jagame isikukoodid kimpudesse (jagamine toimub aasta alusel)
        for (long isikukood : jrj) {
            int aasta = arvutaAasta(isikukood);
            int indeks = (aasta - 1800) * 400 / 400;
            if (aasta != -1) {
                kimbud.get(indeks).add(isikukood);
            }
        }

        // sorteerime igat kimbu
        for (int i = 0; i < 400; i++) {
            //selline sorteerimis võimalus oli leitud: https://www.baeldung.com/java-8-sort-lambda
            kimbud.get(i).sort((a, b) -> {
                //võrdleme kuu järgi
                int kuuA = (int) ((a / 1000000) % 100);
                int kuuB = (int) ((b / 1000000) % 100);
                if (kuuA != kuuB) {
                    return Integer.compare(kuuA, kuuB);
                }

                //võrdleme päeva järgi
                int päevA = (int) ((a / 10000) % 100);
                int päevB = (int) ((b / 10000) % 100);
                if (päevA != päevB) {
                    return Integer.compare(päevA, päevB);
                }

                //võrdleme järjekorranumbri järgi
                int jrkA = (int) ((a / 10) % 1000);
                int jrkB = (int) ((b / 10) % 1000);
                if (jrkA != jrkB) {
                    return Long.compare(jrkA, jrkB);
                }

                //võrdleme esimese numbri järgi
                int esimeneNrA = (int) (a / 10000000000L);
                int esimeneNrB = (int) (b / 10000000000L);
                if (esimeneNrA != esimeneNrB) {
                    return Integer.compare(esimeneNrA, esimeneNrB);
                } else {
                    return Long.compare(a, b);
                }
            });
        }

        //põimime sorteeritud kimbud tagasi
        int indeks = 0;
        for (int i = 0; i < 400; i++) {
            for (long value : kimbud.get(i)) {
                jrj[indeks++] = value;
            }
        }
    }

    private static int arvutaAasta(long id) {
        int sajandiNumber = (int) ((id / 10000000000L) % 10);
        int aasta = (int) ((id / 100000000) % 100);
        int sajandiVäärtus = sajand(sajandiNumber);
        aasta = sajandiVäärtus * 100 + aasta;
        return aasta;
    }

    public static void main(String[] args) {
        long[] isikukoodid = new long[100000];
        for (int i = 0; i < 100000; i++) {
            isikukoodid[i] = genereeriIsikukood();
        }
        sort(isikukoodid);
    }
}
