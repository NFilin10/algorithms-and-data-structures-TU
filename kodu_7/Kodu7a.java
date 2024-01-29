/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö. Ülesanne nr 7A
 * Teema: Kaugusalgoritmid
 *
 * Autor: Nikita Filin
 *
 *****************************************************************************/


import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Kodu7a {

    /**
     * Meetod leiab need linnad, mis on k tankimise kaugusel
     * @param lähtelinn linn millest otsimine algab
     * @param x kilomeetrite arv ühel tankimisel
     * @param k tankimiste arv
     * @param linnad vastavad linnad
     * @param M naabrusmaatriks
     * @return
     */
    public static String[] jõuame(String lähtelinn, int x, int k, String[] linnad, int[][] M) {
        //koostama graafi külgnevusstruktuuri
        List<Tipp> graaf = maatriksGraafiks(linnad, M);

        Map<String, Tipp> linnadeTipud = new HashMap<>();
        for (Tipp tipp : graaf) {
            linnadeTipud.put(tipp.info, tipp);
        }

        //Kasutame järjekorda laiuti läbimiseks
        Queue<Tipp> järjekord = new ArrayDeque<>();
        Tipp algusTipp = linnadeTipud.get(lähtelinn);
        algusTipp.x = 0;
        järjekord.add(algusTipp);

        //Siin hoiame juba vaadeldud tipud
        Set<Tipp> vaadeldud = new HashSet<>();
        vaadeldud.add(algusTipp);
        List<String> tulemus = new ArrayList<>();

        while (!järjekord.isEmpty()) {
            Tipp praegune = järjekord.poll();

            //See linn meile ei sobi
            if (praegune.x > k) {
                continue;
            }

            //Läbime praguse tipu kaared
            for (Kaar kaar : praegune.kaared) {
                Tipp sihtTipp = kaar.lõpp;
                //Kontrollime kas tipp vastab nõuetele
                if (kaar.kaal != 0 && kaar.kaal <= x && !vaadeldud.contains(sihtTipp)) {
                    vaadeldud.add(sihtTipp);
                    //Kuna kaar on läbitud, see tähendab, et toimus tankimine
                    sihtTipp.x = praegune.x + 1;
                    if (sihtTipp.x == k) tulemus.add(sihtTipp.info);
                    järjekord.add(sihtTipp);
                }
            }
        }
        return tulemus.toArray(new String[0]);
    }


    /**
     * Meetod teisendab naabrusmaatriksi graafiks
     * @param linnad vastavad linnad
     * @param M naabrusmaatriksi
     * @return Graaf
     */
    private static List<Tipp> maatriksGraafiks(String[] linnad, int[][] M) {
        List<Tipp> graaf = new ArrayList<>();
        for (String linn : linnad) {
            graaf.add(new Tipp(linn));
        }

        for (int mitmesTipp = 0; mitmesTipp < linnad.length; mitmesTipp++) {
            for (int mitmesSihttipp = 0; mitmesSihttipp < linnad.length; mitmesSihttipp++) {
                int kaugus = M[mitmesTipp][mitmesSihttipp];
                if (mitmesTipp != mitmesSihttipp && kaugus >= 0){
                    Tipp algus = graaf.get(mitmesTipp);
                    Tipp lopp = graaf.get(mitmesSihttipp);
                    Kaar kaar = new Kaar(algus, lopp, kaugus);
                    algus.kaared.add(kaar);
                }
            }
        }
        return graaf;
    }



    public static void main(String[] args) throws IOException {

        File fail = new File("linnade_kaugused.tsv");

        List<String> read = Files.readAllLines(fail.toPath());

        String[] linnad = read.get(0).split("\t");
        int n = read.size() - 1;
        int[][] M = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] väärtused = read.get(i + 1).split("\t");
            for (int j = 0; j < väärtused.length; j++)
                M[i][j] = Integer.parseInt(väärtused[j]);
        }


        String lähtelinn = "Antsla";
        int x = 2147483647;
        int k = 2;

        String[] tulemus = jõuame(lähtelinn, x, k, linnad, M);
        System.out.println(Arrays.toString(tulemus));

    }
}
