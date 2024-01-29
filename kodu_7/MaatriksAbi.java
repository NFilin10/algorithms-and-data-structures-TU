import ee.ut.dendroloj.Dendrologist;
import ee.ut.dendroloj.GraphCanvas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class MaatriksAbi {

    // Abimeetod andmete lugemiseks

    public static NimedegaNaabrusmaatriks loeNaabrusmaatriks(File fail) throws IOException {
        List<String> read = Files.readAllLines(fail.toPath());
        // Loeme esimeselt realt nimed
        String[] nimed = read.get(0).split("\t");
        // Loeme naabrusmaatriksi
        int n = read.size() - 1;
        int[][] M = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] väärtused = read.get(i + 1).split("\t");
            for (int j = 0; j < väärtused.length; j++)
                M[i][j] = Integer.parseInt(väärtused[j]);
        }
        return new NimedegaNaabrusmaatriks(M, nimed);
    }

    // Abimeetodid graafide kuvamiseks dendroloj abil

    public static void kuvaGraaf(NimedegaNaabrusmaatriks maatriks) {
        kuvaGraaf(maatriks.M, maatriks.nimed);
    }

    public static void kuvaGraaf(int[][] M, String[] nimed) {
        // Märkus: See meetod eeldab ilma kontrollimata, et M on ruudukujuline.
        GraphCanvas<Integer> lõuend = new GraphCanvas<>();
        for (int i = 0; i < M.length; i++)
            lõuend.drawVertex(i, nimed[i]);
        for (int i = 0; i < M.length; i++)
            for (int j = i + 1; j < M.length; j++)
                if (M[i][j] == M[j][i]) {
                    // Naabrusmaatriks on sümmeetriline
                    if (M[i][j] > -1) lõuend.drawEdge(i, j, Integer.toString(M[i][j]));
                } else {
                    // Naabrusmaatriks ei ole sümmeetriline
                    if (M[i][j] > -1) lõuend.drawDirectedEdge(i, j, Integer.toString(M[i][j]));
                    if (M[j][i] > -1) lõuend.drawDirectedEdge(j, i, Integer.toString(M[j][i]));
                }
        Dendrologist.drawGraph(lõuend);
    }

    // Juhusliku graafi maatriksi genereerimine

    public static NimedegaNaabrusmaatriks juhuMaatriks(int tippe, int maxKaarepikkus, double ühendatuseTõenäosus) {
        String[] nimed = new String[tippe];
        for (int i = 0; i < nimed.length; i++)
            if (tippe <= 26) nimed[i] = "" + (char) (i + 65);
            else nimed[i] = "T" + (i);
        int[][] maatriks = new int[tippe][tippe];
        for (int rida = 0; rida < tippe; rida++)
            for (int veer = 0; veer < tippe; veer++)
                if (rida == veer) maatriks[rida][veer] = 0;
                else if (Math.random() > ühendatuseTõenäosus) maatriks[rida][veer] = -1;
                else maatriks[rida][veer] = 1 + (int) (Math.random() * maxKaarepikkus);
        return new NimedegaNaabrusmaatriks(maatriks, nimed);
    }
}
