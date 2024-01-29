import java.util.Arrays;
import java.util.Collections;

public class Kodu1Test {
    public static void main(String[] args) {

        int[] jrj1 = juhuslikJrj(100000000);
        long start2 = System.currentTimeMillis();
        shellSort(jrj1);
        long stop2 = System.currentTimeMillis();
        System.out.println("n = " + 10000000 + " " );
        System.out.println("Aega kulus " + (stop2 - start2) + " milliskundit");
        System.out.println(1000000*(double)(stop2-start2) / (10000000 * Math.log(10000000)) + "\n");


        Arrays.sort(jrj1);
        long start22 = System.currentTimeMillis();
        shellSort(jrj1);
        long stop22 = System.currentTimeMillis();
        System.out.println("n = " + 10000000 + " " );
        System.out.println("Aega kulus " + (stop22 - start22) + " milliskundit");
        System.out.println(1000000*(double)(stop22-start22) / (10000000 * Math.log(10000000)) + "\n");


        //mullimeetodi testimine
//        System.out.println("mullimeetodi testimine");
//        int[] testJrjSort =juhuslikJrj(1000000);
//        Arrays.sort(testJrjSort);
//        int[][] äärejuhtBubble = {{1}, {2, 1}, testJrjSort , {}};
//        int[][] äärejuhtShell = äärejuhtBubble.clone();
//        int[][] äärejuhtArrSort = äärejuhtBubble.clone();
//        int[] n = {1, 2, 10000, 0};

//        for (int i = 1000; i < 120000; i+=10000) {
//            long start1 = System.currentTimeMillis();
//            bubbleSort(juhuslikJrj(i));
//            long stop1 = System.currentTimeMillis();
////            System.out.println(i + " " + (stop - start));
//            System.out.println("n = " + i + " " );
//            System.out.println("Aega kulus " + (stop1 - start1) + " milliskundit");
//            System.out.println(1000000*(double)(stop1-start1) / Math.pow(i, 2) + "\n");
//
//        }
//
//        System.out.println("Äärejuhud: ");
//        for (int i = 0; i < äärejuhtBubble.length; i++) {
//            long start11 = System.currentTimeMillis();
//            bubbleSort(äärejuhtBubble[i]);
//            long stop11 = System.currentTimeMillis();
////            System.out.println(n[i] + " " + (stop - start));
//            System.out.println("n = " + n[i] + " " );
//            System.out.println("Aega kulus " + (stop11 - start11) + " milliskundit");
//            System.out.println(1000000*(double)(stop11-start11) / Math.pow(i, 2) + "\n");
//        }
//
//        System.out.println("-".repeat(100));

        //Shelli meetodi testimine
//        System.out.println("Shelli testimine");
//        for (int i = 10000; i < 44020000; i+=4000000) {
//            long start2 = System.currentTimeMillis();
//            shellSort(juhuslikJrj(i));
//            long stop2 = System.currentTimeMillis();
//            System.out.println("n = " + i + " " );
////            System.out.println(i + " " + (stop - start));
//            System.out.println("Aega kulus " + (stop2 - start2) + " milliskundit");
//            System.out.println(1000000*(double)(stop2-start2) / (i * Math.log(i)) + "\n");
//        }
//
//        System.out.println("Äärejuhud: ");
//        for (int i = 0; i < äärejuhtShell.length; i++) {
//            long start22 = System.currentTimeMillis();
//            shellSort(äärejuhtShell[i]);
//            long stop22 = System.currentTimeMillis();
////            System.out.println(n[i] + " " + (stop - start));
//            System.out.println("n = " + n[i] + " " );
//            System.out.println("Aega kulus " + (stop22 - start22) + " milliskundit");
//            System.out.println(1000000*(double)(stop22-start22) / (i * Math.log(i)) + "\n");
//        }
//
//        System.out.println("-".repeat(100));
//
//        System.out.println("Arrays sort");
//        for (int i = 10000; i < 44020000; i+=4000000) {
//            long start3 = System.currentTimeMillis();
//            int[] jrj = juhuslikJrj(i);
//            Arrays.sort(jrj);
//            long stop3 = System.currentTimeMillis();
//            System.out.println("n = " + i + " " );
////            System.out.println(i + " " + (stop - start));
//            System.out.println("Aega kulus " + (stop3 - start3) + " milliskundit");
//            System.out.println(1000000*(double)(stop3-start3) / (i * Math.log(i)) + "\n");
//        }
//
//        System.out.println("Äärejuhud: ");
//        for (int i = 0; i < äärejuhtArrSort.length; i++) {
//            long start33 = System.currentTimeMillis();
//            int[] jrjÄär = äärejuhtArrSort[i];
//            Arrays.sort(jrjÄär);
//            long stop33 = System.currentTimeMillis();
////            System.out.println(n[i] + " " + (stop - start));
//            System.out.println("n = " + n[i] + " " );
//            System.out.println("Aega kulus " + (stop33 - start33) + " milliskundit");
//            System.out.println(1000000*(double)(stop33-start33) / (i * Math.log(i)) + "\n");
//        }


    }

    //https://www.geeksforgeeks.org/bubble-sort/
    public static void bubbleSort(int[] arr)
    {
        int n = arr.length;
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {

                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped)
                if (kasOnSorteeritud(arr)){
//                    System.out.println("Järjend on sorteeritud");
                    break;
                }
                else {
//                    System.out.println("Järjend ei ole sorteeritud");
                    break;
                }
        }
    }



    //https://www.geeksforgeeks.org/shellsort/
    public static void shellSort(int[] arr)
    {
        int n = arr.length;
        for (int gap = n/2; gap > 0; gap /= 2)
        {
            for (int i = gap; i < n; i += 1)
            {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap)
                    arr[j] = arr[j - gap];

                arr[j] = temp;
            }
        }
//        if (kasOnSorteeritud(arr)){
//            System.out.println("Järjend on sorteeritud");
//        }
//        else {
//            System.out.println("Järjend ei ole sorteeritud");
//        }
    }

    public static boolean kasOnSorteeritud(int[] arr){
        for (int i = 0; i < arr.length-1; i++) {
            if (arr[i] > arr[i+1]){
                return false;
            }
        }
        return true;
    }


    public static int[] juhuslikJrj(int length) {
        int[] jrj = new int[length];
        for (int i = 0; i < length; i++)
            jrj[i] = (int) (Math.random() * 10000);
        return jrj;
    }





}
