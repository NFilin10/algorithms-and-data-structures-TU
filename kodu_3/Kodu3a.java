/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö. Ülesanne nr 3A
 * Teema: Magasin ja järjekord
 *
 * Autor: Nikita Filin
 *
 *****************************************************************************/


import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;

class Kodu3a {

    /**
     * Meetod läbib failisüsteemi tasemeti, alates etteantud kataloogist ja väljastab alamkataloogide ning failide nimed.
     * @param tee kataloogi tee millest alustada
     */
    public static void failipuu(String tee) {
        //järekord failide jaoks
        Queue<File> failideJrk = new ArrayDeque<>();
        //järjekord taandete jaoks
        Queue<Integer> sügavusJrk = new ArrayDeque<>();

        File esmane = new File(tee);
        failideJrk.add(esmane);
        sügavusJrk.add(0);

        while (!failideJrk.isEmpty()) {
            File fail = failideJrk.poll();
            int vahe = sügavusJrk.poll();
            //kui fail on sobiva suurusega, siis printime välja
            if (fail.isFile()) {
                double suurus = Math.round((fail.length() / 1024.0) * 100.0) / 100.0;
                if (suurus <= 500)  System.out.println("\t".repeat(vahe) + fail.getName() + " (" + suurus + " KB)");
               //juht, kus fail on kaust
            } else if (fail.isDirectory()) {
                System.out.println("\t".repeat(vahe) + "[" + fail.getName() + "]");

                //itereerime faile, mis on kaustas ja lisame neid järjekorda
                File[] files = fail.listFiles();
                if (files != null) {
                    for (File failKaustas : files) {
                        failideJrk.add(failKaustas);
                        sügavusJrk.add(vahe + 1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        failipuu("/Users/nikita_filin/Desktop/TU");
    }
}
