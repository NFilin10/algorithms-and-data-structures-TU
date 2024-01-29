public class Test {

    public static KOTipp lisaKirje(KOTipp juur, int väärtus) {
        if (juur == null) {
            return new KOTipp(väärtus);
        } else if (juur.väärtus > väärtus) {
            juur.v = lisaKirje(juur.v, väärtus);
        } else if (juur.väärtus < väärtus) {
            juur.p = lisaKirje(juur.p, väärtus);
        } else {
            // Duplicate key, add to the right subtree
            juur.p = lisaKirje(juur.p, väärtus);
        }
        return rebalance(juur);
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
            juur = rebalance(juur);
        }
        return juur;
    }

//    public static KOTipp liidaAVLpuud(KOTipp avl1, KOTipp avl2) {
//        throw new UnsupportedOperationException();
//    }


    public static void updateHeight(KOTipp n) {
        n.x = 1 + Math.max(height(n.v), height(n.p));
    }

    public static int height(KOTipp n) {
        return n == null ? -1 : n.x;
    }

    static int getBalance(KOTipp n) {
        return (n == null) ? 0 : height(n.p) - height(n.v);
    }

    public static KOTipp rotateRight(KOTipp y) {
        KOTipp x = y.v;
        KOTipp z = x.p;
        x.p = y;
        y.v = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    public static KOTipp rotateLeft(KOTipp y) {
        KOTipp x = y.p;
        KOTipp z = x.v;
        x.v = y;
        y.p = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    public static KOTipp rebalance(KOTipp z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.p.p) > height(z.p.v)) {
                z = rotateLeft(z);
            } else {
                z.p = rotateRight(z.p);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.v.v) > height(z.v.p))
                z = rotateRight(z);
            else {
                z.v = rotateLeft(z.v);
                z = rotateRight(z);
            }
        }
        return z;
    }

    public static KOTipp mostLeftChild(KOTipp node) {
        while (node.v != null) {
            node = node.v;
        }
        return node;
    }

    public static KOTipp liidaAVLpuud(KOTipp avl1, KOTipp avl2) {
        if (avl1 == null) {
            return avl2;
        }
        if (avl2 == null) {
            return avl1;
        }

        int rootValue = avl1.väärtus; // Choose any root value (e.g., from avl1)

        // Create a new root for the merged AVL tree
        KOTipp mergedRoot = new KOTipp(rootValue);

        // Recursively merge the left and right subtrees
        mergedRoot.v = liidaAVLpuud(avl1.v, avl2);
        mergedRoot.p = liidaAVLpuud(avl1.p, avl2);

        // Rebalance the merged AVL tree
        return rebalance(mergedRoot);
    }


}


/**



 import ee.ut.dendroloj.Dendrologist;

 import java.util.LinkedList;
 import java.util.Queue;
 import java.util.Vector;

 public class Kodu5 {

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
 if (juur == null) return null;
 else if (väärtus < juur.väärtus) juur.v = eemaldaKirje(juur.v, väärtus);
 else if (väärtus > juur.väärtus) juur.p = eemaldaKirje(juur.p, väärtus);
 else if (juur.v == null && juur.p == null) return null;
 else if (juur.v == null) return juur.p;
 else if (juur.p == null) return juur.v;
 else {
 int paremaVähim = leiaVähim(juur.p);
 juur.p = eemaldaKirje1(juur.p, paremaVähim);
 juur.väärtus = paremaVähim;
 }
 uuendaKorgus(juur);
 juur = balanseering(juur);
 return juur;
 }

 public static KOTipp eemaldaKirje1(KOTipp juur, int väärtus) {
 if (juur == null) {
 return null;
 } else if (väärtus < juur.väärtus) {
 juur.v = eemaldaKirje1(juur.v, väärtus);
 } else if (väärtus > juur.väärtus) {
 juur.p = eemaldaKirje1(juur.p, väärtus);
 } else {
 // Current node needs to be deleted
 if (juur.v == null && juur.p == null) {
 return null;
 } else if (juur.v == null) return juur.p;
 else if (juur.p == null) return juur.v;
 else{
 juur.v = eemaldaKirje1(juur.v, väärtus);
 }
 }
 uuendaKorgus(juur);
 return balanseering(juur);
 }

 //        if (juur == null) return null;
 //        else if (väärtus < juur.väärtus)
 //            juur.v = eemaldaKirje(juur.v, väärtus);
 //
 //        else if (väärtus > juur.väärtus)
 //            juur.p = eemaldaKirje(juur.p, väärtus);
 //        else {
 //            if (juur.v == null && juur.p == null)
 //                return null;
 //
 //            else {
 //                int paremaVähim = leiaVähim(juur.p);
 //                juur.p = eemaldaKirje(juur.p, paremaVähim); // Use the same method for removal
 //                juur.väärtus = paremaVähim;
 //            }
 //        }
 //        uuendaKorgus(juur);
 //        return balanseering(juur);
 //}


 private static int leiaVähim(KOTipp juur) {
 if (juur.v == null) return juur.väärtus;
 return leiaVähim(juur.v);
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


 public static void uuendaKorgus(KOTipp juur) {
 juur.x = 1 + Math.max(korgus(juur.v), korgus(juur.p));
 }


 public static int korgus(KOTipp juur) {
 if (juur == null) {
 return -1;
 } else {
 return juur.x;
 }
 }

 public static int kontrolliBalans(KOTipp juur) {

 return korgus(juur.p) - korgus(juur.v);
 }

 public static KOTipp vasak(KOTipp juur) {
 KOTipp parem = juur.p; //57
 KOTipp paremVasak = parem.v; //54

 parem.v = juur;
 juur.p = paremVasak;

 uuendaKorgus(juur);
 uuendaKorgus(parem);
 return parem;

 }


 public static KOTipp parem(KOTipp juur) {
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


 public static void main(String[] args) {
 //        KOTipp avlTree = new KOTipp(46,
 //                new KOTipp(40, new KOTipp(39, null, new KOTipp(44)), new KOTipp(45)),
 //                new KOTipp(52, new KOTipp(50), new KOTipp(57, new KOTipp(54), new KOTipp(60))));


 //        KOTipp avlTree = new KOTipp(7, new KOTipp(2, null, new KOTipp(5)), new KOTipp(9, new KOTipp(8), new KOTipp(11)));

 KOTipp avlTree = null;
 //
 //        avlTree = Kodu5.lisaKirje(avlTree, 2);
 //        avlTree = Kodu5.lisaKirje(avlTree, 1);
 avlTree = Kodu5.lisaKirje(avlTree, 3);
 avlTree = Kodu5.lisaKirje(avlTree, 2);
 avlTree = Kodu5.lisaKirje(avlTree, 7);
 avlTree = Kodu5.lisaKirje(avlTree, 8);
 avlTree = Kodu5.lisaKirje(avlTree, 1);
 avlTree = Kodu5.lisaKirje(avlTree, 5);
 avlTree = Kodu5.lisaKirje(avlTree, 6);
 avlTree = Kodu5.lisaKirje(avlTree, 4);
 avlTree = Kodu5.eemaldaKirje(avlTree, 3);

 avlTree = Kodu5.lisaKirje(avlTree, 3);
 avlTree = Kodu5.lisaKirje(avlTree, 9);
 avlTree = Kodu5.eemaldaKirje(avlTree, 8);
 avlTree = Kodu5.lisaKirje(avlTree, 8);
 avlTree = Kodu5.eemaldaKirje(avlTree, 7);

 avlTree = Kodu5.eemaldaKirje(avlTree, 4);
 //        KOTipp.kuvaKahendotsimispuu(avlTree);

 avlTree = Kodu5.eemaldaKirje(avlTree, 3);
 //        KOTipp.kuvaKahendotsimispuu(avlTree);

 avlTree = Kodu5.eemaldaKirje(avlTree, 2);


 // Print the AVL tree

 KOTipp.kuvaKahendotsimispuu(avlTree);


 }


 }

 */