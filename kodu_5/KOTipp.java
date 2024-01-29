
import ee.ut.dendroloj.Dendrologist;

public class KOTipp {
    int väärtus;
    KOTipp v;
    KOTipp p;
    int x; // abiväli



    KOTipp(int väärtus, KOTipp v, KOTipp p) {
        this.väärtus = väärtus;
        this.v = v;
        this.p = p;
    }

    KOTipp(int väärtus) {
        this.väärtus = väärtus;
    }

    public static void kuvaKahendotsimispuu(KOTipp juurTipp) {
        Dendrologist.drawBinaryTree(juurTipp, t -> Integer.toString(t.väärtus), t -> t.v, t -> t.p);
    }

    /**
     * @param juur Antud puu juurtipp.
     * @return Juhuslik sorteeritud täisarvumassiiv, kus elementide arv on sama, mis argumendiks antud tipus "juur".
     */
    public static int[] juhusisu(KOTipp juur) {
        int tippe = tippe(juur);
        int[] täidis = new int[tippe];
        int sisu = (int) (Math.random() * 10);
        for (int i = 0; i < tippe; i++) {
            täidis[i] = sisu;
            sisu += 1 + (int) (Math.random() * 3);
        }
        return täidis;
    }

    private static int tippe(KOTipp juur) {
        if (juur == null) return 0;
        return 1 + tippe(juur.v) + tippe(juur.p);
    }
}
