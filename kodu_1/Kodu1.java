/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö. Ülesanne nr 1
 * Teema: Sorteerimismeetodite ajalise keerukuse võrdlus
 *
 * Autor: Nikita Filin
 *****************************************************************************/

import java.util.Arrays;


public class Kodu1 {
    public static void main(String[] args) {
        //mullimeetodi testimine
        System.out.println("Mullimeetodi testimine");
        //äärejuhud
        int[] testJrjSort =juhuslikJrj(10000);
        Arrays.sort(testJrjSort);
        int[][] äärejuhtBubble = {{1}, {2, 1}, testJrjSort , {}};
        int[][] äärejuhtShell = äärejuhtBubble.clone();
        int[][] äärejuhtArrSort = äärejuhtBubble.clone();
        int[] n = {1, 2, 10000, 0};

        for (int i = 1000; i < 120000; i+=10000) {
            long start1 = System.currentTimeMillis();
            bubbleSort(juhuslikJrj(i));
            long stop1 = System.currentTimeMillis();
            System.out.println("n = " + i);
            System.out.println("Aega kulus " + (stop1 - start1) + " millisekundit");
            System.out.println("T(n)/n^2: " + 1000000*(double)(stop1-start1) / Math.pow(i, 2) + "\n");
        }

        System.out.println("Äärejuhud: ");
        for (int i = 0; i < äärejuhtBubble.length; i++) {
            long start11 = System.currentTimeMillis();
            bubbleSort(äärejuhtBubble[i]);
            long stop11 = System.currentTimeMillis();
            System.out.println("n = " + n[i]);
            System.out.println("Aega kulus " + (stop11 - start11) + " millisekundit");
            System.out.println("T(n)/n^2: " + 1000000*(double)(stop11-start11) / Math.pow(i, 2) + "\n");
        }

        System.out.println("-".repeat(100));

        //Shelli meetodi testimine
        System.out.println("Shelli meetodi testimine");
        //paremaks võrdlemiseks võib kasutada: int i = 10000; i < 44020000; i+=4000000
        for (int i = 1000; i < 120000; i+=10000) {
            long start2 = System.currentTimeMillis();
            shellSort(juhuslikJrj(i));
            long stop2 = System.currentTimeMillis();
            System.out.println("n = " + i);
            System.out.println("Aega kulus " + (stop2 - start2) + " millisekundit");
            System.out.println("T(n)/nlog(n) " + 1000000*(double)(stop2-start2) / (i * Math.log(i)) + "\n");
        }

        System.out.println("Äärejuhud: ");
        for (int i = 0; i < äärejuhtShell.length; i++) {
            long start22 = System.currentTimeMillis();
            shellSort(äärejuhtShell[i]);
            long stop22 = System.currentTimeMillis();
            System.out.println("n = " + n[i]);
            System.out.println("Aega kulus " + (stop22 - start22) + " millisekundit");
            System.out.println("T(n)/nlog(n) " + 1000000*(double)(stop22-start22) / (i * Math.log(i)) + "\n");
        }

        System.out.println("-".repeat(100));

        System.out.println("Arrays.sort meetodi testimine");
        //paremaks võrdlemiseks võib kasutada: int i = 10000; i < 44020000; i+=4000000
        for (int i = 1000; i < 120000; i+=10000) {
            int[] jrj = juhuslikJrj(i);
            long start3 = System.currentTimeMillis();
            Arrays.sort(jrj);
            long stop3 = System.currentTimeMillis();

            System.out.println("n = " + i);
            System.out.println("Aega kulus " + (stop3 - start3) + " millisekundit");
            System.out.println("T(n)/nlog(n) " + 1000000*(double)(stop3-start3) / (i * Math.log(i)) + "\n");
        }

        System.out.println("Äärejuhud: ");
        for (int i = 0; i < äärejuhtArrSort.length; i++) {
            int[] jrjÄär = äärejuhtArrSort[i];
            long start33 = System.currentTimeMillis();
            Arrays.sort(jrjÄär);
            long stop33 = System.currentTimeMillis();

            System.out.println("n = " + n[i]);
            System.out.println("Aega kulus " + (stop33 - start33) + " millisekundit");
            System.out.println("T(n)/nlog(n) " + 1000000*(double)(stop33-start33) / (i * Math.log(i)) + "\n");
        }
    }


    /**
     * Meetod kasutab mullimeetodi algoritmi järjendi sorteerimiseks mittekasvavalt
     * @param jrj antud järjen
     * Allikas: https://www.geeksforgeeks.org/bubble-sort/
     */
    public static void bubbleSort(int[] jrj)
    {
        int n = jrj.length;
        int i, j, ajutine;
        for (i = 0; i < n - 1; i++) {
            //elementide kontroll
            for (j = 0; j < n - i - 1; j++) {
                if (jrj[j] < jrj[j + 1]) {

                    ajutine = jrj[j];
                    jrj[j] = jrj[j + 1];
                    jrj[j + 1] = ajutine;
                }
            }
        }

        //kontrollime, kas algoritm töötas õigesti
        if (kasOnSorteeritud(jrj)){
            System.out.println("Järjend on sorteeritud");

        }
        else {
            System.out.println("Järjend ei ole sorteeritud");
        }
    }


    /**
     * Meetod kasutab Shelli algoritmi järjendi sorteerimiseks mittekasvavalt
     * @param jrj antud järjend
     * Allikas: https://www.geeksforgeeks.org/shellsort/
     */
    public static void shellSort(int[] jrj)
    {
        int n = jrj.length;
        for (int vahemik = n/2; vahemik > 0; vahemik /= 2)
        {
            for (int i = vahemik; i < n; i += 1)
            {
                int ajutine = jrj[i];
                int j;
                for (j = i; j >= vahemik && jrj[j - vahemik] < ajutine; j -= vahemik)
                    jrj[j] = jrj[j - vahemik];

                jrj[j] = ajutine;
            }
        }

        if (kasOnSorteeritud(jrj)){
            System.out.println("Järjend on sorteeritud");
        }
        else {
            System.out.println("Järjend ei ole sorteeritud");
        }
    }

    /**
     * Meetod kontrollib, kas antud järjend on sorteeritud
     * @param jrj antud järjend
     * @return true, kui on sorteeritus, vastasel juhul false
     */
    public static boolean kasOnSorteeritud(int[] jrj){
        for (int i = 0; i < jrj.length-1; i++) {
            if (jrj[i] < jrj[i+1]){
                return false;
            }
        }
        return true;
    }


    /**
     * Meetod genereerib juhusliku järjendi etteantud pikkusega
     * @param n järhenfi pikkus
     * @return genereeritud järjend
     */
    public static int[] juhuslikJrj(int n) {
        int[] jrj = new int[n];
        for (int i = 0; i < n; i++)
            jrj[i] = (int) (Math.random() * 10000);
        return jrj;
    }
}
