import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.GraphCanvas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Abi {

    public static Tipp leiaTipp(String sisu, List<Tipp> tipud) {
        for (Tipp tipp : tipud) if (tipp.info.equals(sisu)) return tipp;
        return null;
    }

    // Abimeetodid andmete lugemiseks

    public static List<Tipp> loeKülgnevusstruktuur(File fail) throws IOException {
        List<String> read = Files.readAllLines(fail.toPath());
        Map<String, Tipp> tipud = new HashMap<>();
        for (String rida : read) {
            String[] väärtused = rida.split("\t");
            Tipp tipp = new Tipp(väärtused[0]);
            tipud.put(tipp.info, tipp);
        }
        for (String rida : read) {
            String[] väärtused = rida.split("\t");
            Tipp tipp = tipud.get(väärtused[0]);
            for (int i = 2; i < väärtused.length; i++)
                tipp.kaared.add(new Kaar(tipp, tipud.get(väärtused[i]), 1));
        }
        return new ArrayList<>(tipud.values());
    }

    // Abimeetodid graafide kuvamiseks dendroloj abil

    public static void kuvaGraafKaaludeta(List<Tipp> tipud) {
        GraphCanvas<Tipp> lõuend = new GraphCanvas<>();
        for (Tipp tipp : tipud) {
            lõuend.drawVertex(tipp, tipp.info);
            for (Kaar kaar : tipp.kaared)
                lõuend.drawDirectedEdge(kaar.alg, kaar.lõpp);
        }
        Dendrologist.drawGraph(lõuend);
    }

    public static void kuvaGraaf(List<Tipp> tipud) {
        GraphCanvas<Tipp> lõuend = new GraphCanvas<>();
        for (Tipp tipp : tipud) {
            lõuend.drawVertex(tipp, tipp.info);
            for (Kaar kaar : tipp.kaared)
                lõuend.drawDirectedEdge(kaar.alg, kaar.lõpp, Integer.toString(kaar.kaal));
        }
        Dendrologist.drawGraph(lõuend);
    }

    // Juhusliku graafi genereerimine

    public static List<Tipp> loenguGraaf() {
        List<Tipp> graaf = new ArrayList<>();
        for (char tähis : "abcdefgh".toCharArray())
            graaf.add(new Tipp("" + tähis));
        ühenda("a", "b", graaf);
        ühenda("a", "c", graaf);
        ühenda("a", "d", graaf);
        ühenda("b", "c", graaf);
        ühenda("d", "c", graaf);
        ühenda("c", "e", graaf);
        ühenda("d", "f", graaf);
        ühenda("d", "g", graaf);
        ühenda("e", "h", graaf);
        ühenda("f", "h", graaf);
        ühenda("g", "h", graaf);
        return graaf;
    }

    private static void ühenda(String a, String b, List<Tipp> graaf) {
        leiaTipp(a, graaf).kaared.add(new Kaar(leiaTipp(a, graaf), leiaTipp(b, graaf), 1));
    }

    public static List<Tipp> juhuslikGraaf(int tippe, double ühendatuseTõenäosus) {
        return juhuslikGraaf(tippe, ühendatuseTõenäosus, 1, 1);
    }

    public static List<Tipp> juhuslikGraaf(int tippe, double ühendatuseTõenäosus, int min, int max) {
        List<Tipp> graaf = new ArrayList<>();
        Collections.shuffle(graaf);
        for (int i = 0; i < tippe; i++) graaf.add(new Tipp(Integer.toString(i + 1)));
        for (Tipp tipp1 : graaf)
            for (Tipp tipp2 : graaf)
                if (tipp1 != tipp2 && Math.random() < ühendatuseTõenäosus) {
                    Set<Tipp> läbitud = new HashSet<>();
                    Deque<Tipp> läbida = new ArrayDeque<>();
                    läbitud.add(tipp2);
                    for (Kaar kaar : tipp2.kaared) läbida.push(kaar.lõpp);
                    boolean ring = false;
                    while (!läbida.isEmpty()) {
                        Tipp järgmine = läbida.remove();
                        if (järgmine == tipp1) ring = true;
                        läbitud.add(järgmine);
                        for (Kaar kaar : järgmine.kaared) if (!läbitud.contains(kaar.lõpp)) läbida.push(kaar.lõpp);
                    }
                    if (!ring) tipp1.kaared.add(new Kaar(tipp1, tipp2, min + (int) (Math.random() * (max + 1))));
                }
        return graaf;
    }
}
