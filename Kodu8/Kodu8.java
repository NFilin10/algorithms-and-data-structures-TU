/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö. Ülesanne nr 8
 * Teema: Toespuud
 *
 * Autor: Nikita Filin
 *
 *****************************************************************************/


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;


public class Kodu8 {

    public static void main(String[] args) throws IOException {
        NimedegaKoordinaadid a = loeKoordinaadid(new File("omniva.csv"));
        System.out.println(Arrays.deepToString(toesKruskal(a.nimed, a.K)));
        System.out.println(Arrays.toString(rändkaupmees(a.nimed, a.K)));
    }


    /**
     * Leiab minimaalse kaugusega toesepuu Kruskali algoritmiga.
     *
     * @param nimed Asukohtade nimed
     * @param K     Asukohtade koordinaadid tabelina, kus iga rida on kujul [laiuskraad, pikkuskraad]
     * @return Minimaalse toesepuu kaarte loend, kujul [[i1, j2], [i2, j2], ...], kus i ja j on asukohtade indeksid alates 0-ist
     */
    public static int[][] toesKruskal(String[] nimed, double[][] K) {
        int n = nimed.length;

        List<Tipp> tipud = new ArrayList<>();
        for (String nimi : nimed) {
            tipud.add(new Tipp(nimi));
        }

        //loome kauguste maatriksi
        double[][] kaugusteMaatriks = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    kaugusteMaatriks[i][j] = 0;
                } else {
                    double lai1 = K[i][0];
                    double pik1 = K[i][1];
                    double lai2 = K[j][0];
                    double pik2 = K[j][1];
                    kaugusteMaatriks[i][j] = kaugus(lai1, pik1, lai2, pik2);
                }
            }
        }

        //loome kaarte nimekirja
        List<Kaar> kaared = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double kaal = kaugusteMaatriks[i][j];
                kaared.add(new Kaar(tipud.get(i), tipud.get(j), kaal));
            }
        }

        //sorteerime kaared kaalude järgi
        kaared.sort(Comparator.comparingDouble(e -> e.kaal));


        //Tippude vanemate massiiv
        int[] vanem = new int[n];
        for (int i = 0; i < n; i++) {
            vanem[i] = i;
        }

        List<int[]> tulemus = new ArrayList<>();
        for (Kaar kaar : kaared) {
            int algIndeks = tipud.indexOf(kaar.alg);
            int lõppIndeks = tipud.indexOf(kaar.lõpp);
            int tipp1 = leia(vanem, algIndeks);
            int tipp2 = leia(vanem, lõppIndeks);

            //kui kahe tipu vanemad pole samad, siis lisame kaare tulemusse ja ühendame need klassi puud
            if (tipp1 != tipp2) {
                tulemus.add(new int[]{algIndeks, lõppIndeks});
                ühenda(vanem, tipp1, tipp2);
            }
        }

        return tulemus.toArray(new int[0][]);
    }


    /**
     * Meetod leiab antud tipu juure
     * @param vanem vanemate massiiv
     * @param i otsitava tippu indeks
     * @return leitud juur
     */
    private static int leia(int[] vanem, int i) {
        while (vanem[i] != i) {
            i = vanem[i];
        }
        return i;
    }


    /**
     * Meetod ühendab kaks klassi puud
     * @param vanem vanemate massiiv
     * @param x esimene juur
     * @param y teine juur
     */
    private static void ühenda(int[] vanem, int x, int y) {
        int vanem1 = leia(vanem, x);
        int vanem2 = leia(vanem, y);
        vanem[vanem1] = vanem2;
    }


    /**
     * Leiab lähendi rändkaupmehe probleemile.
     *
     * @param nimed Asukohtade nimed
     * @param K     Asukohtade koordinaadid tabelina, kus iga rida on kujul [laiuskraad, pikkuskraad]
     * @return Rändkaupmehe lähend kui asukohtade läbimise järjestus arvude 0...n-1 permutatsioonina, kus n on tippude arv
     */
    public static int[] rändkaupmees(String[] nimed, double[][] K) {
        int n = nimed.length;

        int[] tee = new int[n];
        boolean[] vaadeldud = new boolean[n];

        //alustame esimesest asukohast
        tee[0] = 0;
        vaadeldud[0] = true;

        for (int i = 1; i < n; i++) {
            int vaadeldavTipp = tee[i - 1];
            int lähimNaaber = -1;
            double minKaugus = Double.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!vaadeldud[j]) {
                    double kaugus = kaugus(K[vaadeldavTipp][0], K[vaadeldavTipp][1], K[j][0], K[j][1]);
                    //kontrollime, et kaugus oleks minimaalne
                    if (kaugus < minKaugus) {
                        //uuendame
                        lähimNaaber = j;
                        minKaugus = kaugus;
                    }
                }
            }

            //lisame leitud naabri tulemusse
            tee[i] = lähimNaaber;
            //määrame, et asukoht on vaadeldud
            vaadeldud[lähimNaaber] = true;
        }

        return tee;
    }


    /**
     * Leiab kahe punkti vahelise kauguse, kasutades valemit siit:
     * https://en.wikipedia.org/wiki/Haversine_formula
     *
     * @param lai1 Esimese punkti laiuskraad
     * @param pik1 Esimese punkti pikkuskraad
     * @param lai2 Teise punkti laiuskraad
     * @param pik2 Teise punkti pikkuskraad
     * @return Punktide vaheline kaugus kilomeetrites
     */
    public static double kaugus(double lai1, double pik1, double lai2, double pik2) {
        double dLaius = Math.pow(Math.sin(Math.toRadians(lai2 - lai1) / 2), 2);
        return 2 * 6371.0088 * Math.asin(Math.sqrt(dLaius +
                (1 - dLaius - Math.pow(Math.sin(Math.toRadians(lai1 + lai2) / 2), 2)) *
                        Math.pow(Math.sin(Math.toRadians(pik2 - pik1) / 2), 2)));
    }


    /**
     * Loeb CSV failist tippude nimed ja koordinaadid.
     * Tagastab koordinaatide massiivi kujul [[laiuskraad_1, pikkuskraad_1], ..., [laiuskraad_n, pikkuskraad_n]]
     * ja nimede massiivi kujul [nimi_1, ..., nimi_n].
     * <p>
     * Mõeldud kasutamiseks failiga omniva.csv.
     */
    static NimedegaKoordinaadid loeKoordinaadid(File fail) throws IOException {
        List<String> read = Files.readAllLines(fail.toPath());
        String[] nimed = new String[read.size()];
        double[][] K = new double[read.size()][];
        for (int i = 0; i < read.size(); i++) {
            String[] väärtused = read.get(i).split(",");
            nimed[i] = väärtused[0];
            K[i] = new double[]{Double.parseDouble(väärtused[1]), Double.parseDouble(väärtused[2])};
        }
        return new NimedegaKoordinaadid(K, nimed);
    }
}



