import java.io.*;
import java.util.BitSet;

/**
 * Abiklass Huffmani algoritmi koodipuu ja bitiajada kompaktselt faili kirjutamiseks ja failist lugemiseks.
 */
public class FailiSisu {

    public final Tipp koodipuu;
    public final boolean[] bitid;

    public FailiSisu(Tipp koodipuu, boolean[] bitid) {
        this.koodipuu = koodipuu;
        this.bitid = bitid;
    }

    public static void kirjutaFaili(File fail, Tipp koodipuu, boolean[] bitid) {
        try (DataOutputStream väljund = new DataOutputStream(new FileOutputStream(fail))) {
            kirjutaKoodipuu(väljund, koodipuu);
            kirjutaBitid(väljund, bitid);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static FailiSisu loeFailist(File fail) {
        try (DataInputStream sisend = new DataInputStream(new FileInputStream(fail))) {
            Tipp koodipuu = loeKoodipuu(sisend);
            boolean[] bitid = loeBitid(sisend);
            return new FailiSisu(koodipuu, bitid);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void kirjutaKoodipuu(DataOutputStream dos, Tipp tipp) throws IOException {
        dos.writeBoolean(tipp != null);
        if (tipp != null) {
            dos.writeBoolean(tipp.v == null && tipp.p == null);
            if (tipp.v == null && tipp.p == null) {
                if (tipp.info == null || tipp.info.length() != 1)
                    throw new IllegalArgumentException("Koodipuu lehttipus peaks olema täpselt üks sümbol, aga oli " + tipp.info);
                dos.writeChar(tipp.info.charAt(0));
            }
            kirjutaKoodipuu(dos, tipp.v);
            kirjutaKoodipuu(dos, tipp.p);
        }
    }

    private static Tipp loeKoodipuu(DataInputStream dis) throws IOException {
        return dis.readBoolean()
                ? new Tipp(dis.readBoolean() ? "" + dis.readChar() : null, loeKoodipuu(dis), loeKoodipuu(dis))
                : null;
    }

    // Märkus lugejale: BitSet on andmestruktuur mis esitab bittide massiivi mis kasvab vastavalt vajadusele.
    // Vaata lisaks https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/BitSet.html.

    private static void kirjutaBitid(DataOutputStream dos, boolean[] bitid) throws IOException {
        BitSet pakitudBitid = new BitSet();
        for (int i = 0; i < bitid.length; i++) {
            pakitudBitid.set(i, bitid[i]);
        }
        dos.writeInt(bitid.length);
        dos.write(pakitudBitid.toByteArray());
    }

    private static boolean[] loeBitid(DataInputStream dis) throws IOException {
        int bittideArv = dis.readInt();
        BitSet pakitudBitid = BitSet.valueOf(dis.readAllBytes());
        boolean[] bitid = new boolean[bittideArv];
        for (int i = 0; i < bittideArv; i++) {
            bitid[i] = pakitudBitid.get(i);
        }
        return bitid;
    }

}