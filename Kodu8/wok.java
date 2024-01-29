import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class wok {


    public static void main(String[] args) throws IOException {
        NimedegaKoordinaadid a = loeKoordinaadid(new File("omniva.csv"));
        System.out.println(Arrays.deepToString(a.K));
        System.out.println(Arrays.deepToString(toesKruskal(a.nimed, a.K)));
        System.out.println(Arrays.toString(rändkaupmees(a.nimed, a.K)));
        System.out.println((rändkaupmees(a.nimed, a.K).length));
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
        List<Tipp> vertices = new ArrayList<>();
        for (String nimi : nimed) {
            vertices.add(new Tipp(nimi));
        }

        // Create a distance matrix from the coordinates
        double[][] distanceMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    double lai1 = K[i][0];
                    double pik1 = K[i][1];
                    double lai2 = K[j][0];
                    double pik2 = K[j][1];
                    distanceMatrix[i][j] = kaugus(lai1, pik1, lai2, pik2);
                }
            }
        }

        List<Kaar> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double kaal = distanceMatrix[i][j];
                edges.add(new Kaar(vertices.get(i), vertices.get(j), kaal));
            }
        }

        edges.sort(Comparator.comparingDouble(e -> e.kaal));

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        List<int[]> mst = new ArrayList<>();
        for (Kaar edge : edges) {
            int xSet = find(parent, vertices.indexOf(edge.alg));
            int ySet = find(parent, vertices.indexOf(edge.lõpp));

            if (xSet != ySet) {
                mst.add(new int[]{vertices.indexOf(edge.alg), vertices.indexOf(edge.lõpp)});
                union(parent, xSet, ySet);
            }
        }

        return mst.toArray(new int[0][]);
    }

    private static int find(int[] parent, int i) {
        if (parent[i] != i)
            parent[i] = find(parent, parent[i]);
        return parent[i];
    }

    private static void union(int[] parent, int x, int y) {
        int xSetParent = find(parent, x);
        int ySetParent = find(parent, y);
        parent[ySetParent] = xSetParent;
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
        int[] tour = new int[n];
        boolean[] visited = new boolean[n];

        // Start from the first location (vertex 0)
        tour[0] = 0;
        visited[0] = true;

        for (int i = 1; i < n; i++) {
            int currentVertex = tour[i - 1];
            int nearestNeighbor = -1;
            double minDistance = Double.MAX_VALUE;

            for (int j = 0; j < n; j++) {
                if (!visited[j]) {
                    double distance = kaugus(K[currentVertex][0], K[currentVertex][1], K[j][0], K[j][1]);
                    if (distance < minDistance) {
                        nearestNeighbor = j;
                        minDistance = distance;
                    }
                }
            }

            tour[i] = nearestNeighbor;
            visited[nearestNeighbor] = true;
        }

        return tour;
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



