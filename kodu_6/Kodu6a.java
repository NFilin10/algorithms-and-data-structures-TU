/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö. Ülesanne nr 6A
 * Teema: Kahendkuhjad
 *
 * Autor: Nikita Filin
 *
 *****************************************************************************/


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class Kodu6a {

    /**
     * Meetod koostab Huffmani koodi puud
     * @param sisu sisu mis peab olema pakitud
     * @return Huffmani koodi puu
     */
    public static Tipp koostaKoodipuu(String sisu) {

        HashMap<String, Integer> sagedusTabel = konstrueeriSagedusTabel(sisu);

        ArrayList<Tipp> puudeList = new ArrayList<>();
        //täidame listi lehttippudega
        for (Map.Entry<String, Integer> entry : sagedusTabel.entrySet()) {
            Tipp tipp = new Tipp(entry.getKey());
            tipp.x = entry.getValue();
            puudeList.add(tipp);
        }

//        //Loome kuhja, kus on sümbolite sagedused
//        Kuhi kuhi = new Kuhi();
//        for (Tipp tipp : puudeList) {
//            kuhi.lisa(tipp.x);
//        }

        //konstrueerime Huffmani puud, meie loodud lehtippudest
        konstrueeriHuffmaniPuu(puudeList);

        Tipp koodiPuuJuur = puudeList.get(0);

        return koodiPuuJuur;
    }


    /**
     * Meetod loob Huffmani puud, kasutades sümbolite leht tippe
     * @param puudeList konstrueeritud puu
     */
    private static void konstrueeriHuffmaniPuu(ArrayList<Tipp> puudeList) {
        //loome kuhja, kus hakkame hoidma sümbolite sagedusi
        Kuhi kuhi = new Kuhi();
        for (Tipp tipp : puudeList) {
            kuhi.lisa(-tipp.x);
        }

        while (puudeList.size() > 1) {
            int vähimSagedus1 = kuhi.kustutaJuurtipp();
            //otsime tippu, mis vastab sagedusele
            Tipp tipp1 = kustutaTippSagedusega(puudeList, -vähimSagedus1);

            int vähimSagedus2 = kuhi.kustutaJuurtipp();
            //otsime tippu, mis vastab sagedusele
            Tipp tipp2 = kustutaTippSagedusega(puudeList, -vähimSagedus2);

            //Loome uue tippu
            Tipp vaheTipp = new Tipp(null);
            vaheTipp.v = tipp1;
            vaheTipp.p = tipp2;
            vaheTipp.x = tipp1.x + tipp2.x;

            puudeList.add(vaheTipp);
            kuhi.lisa(-vaheTipp.x);
        }
    }


    /**
     * Meetod tagastab tippu vastava sagedusega
     * @param puudeList tippude list
     * @param sagedus otsitav sagedus
     * @return vastav tipp
     */
    private static Tipp kustutaTippSagedusega(ArrayList<Tipp> puudeList, int sagedus) {
        Tipp leitud = null;
        Iterator<Tipp> iteraator = puudeList.iterator();
        while (iteraator.hasNext()) {
            Tipp tipp = iteraator.next();
            if (tipp.x == sagedus) {
                leitud = tipp;
                break;
            }
        }
        if (leitud != null) {
            puudeList.remove(leitud);
        }
        return leitud;
    }


    /**
     * Meetod konstrueerib vastava sümboli koodi
     * @param tipp koodi puu
     * @param väärtus vastav sümbol
     * @param tee kood
     * @return sümboli kood
     */
    public static String sümboliKood(Tipp tipp, String väärtus, String tee){
        if (tipp.info != null && tipp.info.equals(väärtus)){
            return tee;
        } else{
            if (tipp.v != null){
                String uusTee = sümboliKood(tipp.v, väärtus, tee+"0");
                if (uusTee != null){
                    return uusTee;
                }
            }
            if (tipp.p != null){
                String uusTee = sümboliKood(tipp.p, väärtus, tee+"1");
                return uusTee;
            }
            return null;
        }
    }


    /**
     * Meetod kodeerib vastava teksti
     * @param puu koodi puu
     * @param sisu tekst, mis peab olema pakitud
     * @return pakitud tekst biti massiivina
     */
    public static boolean[] kodeeri(Tipp puu, String sisu) {
        HashMap<String, Integer> sagedusTabel = konstrueeriSagedusTabel(sisu);

        HashMap<String, String> koodid = new HashMap<>();

        //leime iga sümboli jaoks koodi
        for (String s : sagedusTabel.keySet()) {
            koodid.put(s, sümboliKood(puu, s, ""));
        }

        ArrayList<Boolean> kodeeritud = new ArrayList<>();
        //kodeerime igat sümboli
        for (int i = 0; i < sisu.length(); i++) {
            String tee = koodid.get(String.valueOf(sisu.charAt(i)));
            for (int j = 0; j < tee.length(); j++) {
                if (tee.charAt(j) == '1'){
                    kodeeritud.add(true);
                } else{
                    kodeeritud.add(false);
                }
            }

        }

        boolean[] kodeeritudList = new boolean[kodeeritud.size()];
        for (int i = 0; i < kodeeritudList.length; i++) {
            kodeeritudList[i] = kodeeritud.get(i);
        }

        return kodeeritudList;

    }

    /**
     * Meetod dekodeerib vastava teksti
     * @param koodipuu
     * @param bitid kodeeritud tekst
     * @return dekodeeritud tekst
     */
    public static String dekodeeri(Tipp koodipuu, boolean[] bitid) {
        StringBuilder dekodeeritud = new StringBuilder();
        Tipp tipp = koodipuu;

        for (boolean bit : bitid) {
            if (bit) {
                tipp = tipp.p;
            } else {
                tipp = tipp.v;
            }

            if (tipp.v == null && tipp.p == null) {
                dekodeeritud.append(tipp.info);
                tipp = koodipuu;
            }
        }

        return dekodeeritud.toString();
    }


    /**
     * Meetod konstrueerib sagedus tabeli
     * @param tekst tekst, mis peab olema pakitud
     * @return sagedus tabel
     */
    public static HashMap<String, Integer> konstrueeriSagedusTabel(String tekst){
        HashMap<String, Integer> sagedusTabel = new HashMap<>();
        for (int i = 0; i < tekst.length(); i++) {
            String c = String.valueOf(tekst.charAt(i));
            if (sagedusTabel.containsKey(c)){
                sagedusTabel.put(c, sagedusTabel.get(c) + 1);
            } else{
                sagedusTabel.put(c, 1);
            }
        }
        return sagedusTabel;

    }

    public static void main(String[] args) throws IOException {
        String sisu = Files.readString(new File("Kõrboja_peremees.txt").toPath());

        Tipp koodipuu = koostaKoodipuu(sisu);
        boolean[] bitid = kodeeri(koodipuu, sisu);
        System.out.println(Arrays.toString(bitid));

//        String dekodeeritud = dekodeeri(koodipuu, bitid);
//        System.out.println(dekodeeritud);

        FailiSisu.kirjutaFaili(new File("kodeeritud.dat"), koodipuu, bitid);

        FailiSisu failiSisu = FailiSisu.loeFailist(new File("kodeeritud.dat"));
        String dekodeeritud = dekodeeri(failiSisu.koodipuu, failiSisu.bitid);
        System.out.println(dekodeeritud);
    }

}
