import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Point82 {

    public static KOTipp lisaKirje(KOTipp juur, int väärtus) {
        if (juur == null) {
            return new KOTipp(väärtus);
        } else if (juur.väärtus > väärtus) {
            juur.v = lisaKirje(juur.v, väärtus);
        } else if (juur.väärtus < väärtus) {
            juur.p = lisaKirje(juur.p, väärtus);
        } else {
            juur.p = lisaKirje(juur.p, väärtus);
        }
        uuendaKorgus(juur);

        return balanseering(juur);
    }


    public static KOTipp eemaldaKirje(KOTipp juur, int väärtus) {
        if (juur == null) {
            return juur;
        } else if (juur.väärtus > väärtus) {
            juur.v = eemaldaKirje(juur.v, väärtus);
        } else if (juur.väärtus < väärtus) {
            juur.p = eemaldaKirje(juur.p, väärtus);
        } else {
            if (juur.v == null || juur.p == null) {
                juur = (juur.v == null) ? juur.p : juur.v;
            } else {
                KOTipp mostLeftChild = mostLeftChild(juur.p);
                juur.väärtus = mostLeftChild.väärtus;
                juur.p = eemaldaKirje(juur.p, juur.väärtus);
            }
        }
        if (juur != null) {
            uuendaKorgus(juur);
            juur = balanseering(juur);
        }
        return juur;
    }




    private static KOTipp mostLeftChild(KOTipp juur) {
        KOTipp current = juur;
        while (current.v != null) {
            current = current.v;
        }
        return current;
    }






    public static KOTipp liidaAVLpuud(KOTipp avl1, KOTipp avl2) {
        if (avl1 == null) {
            return avl2;
        } else if (avl2 == null) {
            return avl1;
        }

        int korgus1 = korgus(avl1);
        int korgus2 = korgus(avl2);

        if (korgus1 > korgus2) {
            avl1.p = liidaAVLpuud(avl1.p, avl2);
            uuendaKorgus(avl1);
            return balanseering(avl1);
        } else {
            avl2.v = liidaAVLpuud(avl1, avl2.v);
            uuendaKorgus(avl2);
            return balanseering(avl2);
        }
    }



    public static void uuendaKorgus(KOTipp juur){
        juur.x = 1 + Math.max(korgus(juur.v), korgus(juur.p));
    }


    public static int korgus(KOTipp juur){
        if (juur == null) {
            return -1;
        } else {
            return juur.x;
        }
    }

    public static int kontrolliBalans(KOTipp juur){

        return korgus(juur.p) - korgus(juur.v);
    }

    public static KOTipp vasak(KOTipp juur){
        KOTipp parem = juur.p; //57
        KOTipp paremVasak = parem.v; //54

        parem.v = juur;
        juur.p = paremVasak;

        uuendaKorgus(juur);
        uuendaKorgus(parem);
        return parem;

    }


    public static KOTipp parem(KOTipp juur){
        KOTipp vasak = juur.v;
        KOTipp vasakuParem = vasak.p;

        vasak.p = juur;
        juur.v = vasakuParem;

        uuendaKorgus(juur);
        uuendaKorgus(vasak);
        return vasak;

    }

    public static KOTipp balanseering(KOTipp juur) {
        int balanceFactor = kontrolliBalans(juur);

        // Left-heavy?
        if (balanceFactor < -1) {
            if (kontrolliBalans(juur.v) <= 0) {    // Case 1
                // Rotate right
                juur = parem(juur);
            } else {                                // Case 2
                // Rotate left-right
                juur.v = vasak(juur.v);
                juur = parem(juur);
            }
        }

        // Right-heavy?
        if (balanceFactor > 1) {
            if (kontrolliBalans(juur.p) >= 0) {    // Case 3
                // Rotate left
                juur = vasak(juur);
            } else {                                 // Case 4
                // Rotate right-left
                juur.p = parem(juur.p);
                juur = vasak(juur);
            }
        }

        return juur;
    }



    private static void printTree(KOTipp juur) {
        System.out.println("Tree Structure:");
        printTreeRecursive(juur, 0);
        System.out.println("---------------------");
    }

    // Recursive method to print the tree structure
    private static void printTreeRecursive(KOTipp node, int depth) {
        if (node != null) {
            printTreeRecursive(node.p, depth + 1);

            for (int i = 0; i < depth; i++) {
                System.out.print("    ");
            }

            System.out.println(node.väärtus);

            printTreeRecursive(node.v, depth + 1);
        }
    }




    public static void main(String[] args) {
        KOTipp avlTree = new KOTipp(46,
                new KOTipp(40, new KOTipp(40, null, new KOTipp(44)), new KOTipp(45)),
                new KOTipp(52, new KOTipp(52), new KOTipp(57, new KOTipp(54), new KOTipp(60))));


        printTree(avlTree);

        KOTipp z = eemaldaKirje(avlTree, 46);

        printTree(z);




//        KOTipp a = new KOTipp(1);
//        printTree(a);
//        a = lisaKirje(a, 3);
//        printTree(a);
//        a = lisaKirje(a, 6);
//        printTree(a);
//        a = lisaKirje(a, 2);
//        printTree(a);
//        a = eemaldaKirje(a, 2);
//        printTree(a);
//        a = eemaldaKirje(a, 3);
//        printTree(a);

    }







}