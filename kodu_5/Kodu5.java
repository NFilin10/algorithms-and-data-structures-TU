/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö. Ülesanne nr 5
 * Teema: AVL-puud
 *
 * Autor: Nikita Filin
 *
 *****************************************************************************/


import java.util.ArrayList;
import java.util.List;

public class Kodu5 {
    /**
     * Meetod lisab vastavasse puusse uue kirje
     * @param juur vastava puu juur
     * @param väärtus lisatav väärtus
     * @return uue puu juur
     */
    public static KOTipp lisaKirje(KOTipp juur, int väärtus) {
        if (juur == null) {
            return new KOTipp(väärtus);
        } else if (juur.väärtus > väärtus) {
            juur.v = lisaKirje(juur.v, väärtus);
        } else if (juur.väärtus < väärtus) {
            juur.p = lisaKirje(juur.p, väärtus);
        } else {
            //sama võtmega uus kirje
            juur.p = lisaKirje(juur.p, väärtus);
        }

        //uuendame kõrgused ja tasakaalustame
        uuendaKõrgus(juur);
        return tasakaalusta(juur);
    }


    /**
     * Meetod eemaldab puust kirje
     * @param juur vastava puu juur
     * @param väärtus eemaldatav väärtus
     * @return uue puu juur
     */
    public static KOTipp eemaldaKirje(KOTipp juur, int väärtus) {
        if (juur == null) return null;
        else if (väärtus < juur.väärtus) juur.v = eemaldaKirje(juur.v, väärtus);
        else if (väärtus > juur.väärtus) juur.p = eemaldaKirje(juur.p, väärtus);
        //uuritavas tipus on kustutatav võti
        else if (juur.v == null && juur.p == null) return null;
        else if (juur.v == null) return juur.p;
        else if (juur.p == null) return juur.v;
        //juurel on kaks haru
        else {
            //leiame parema haru vähima elemendi
            int paremaVähim = leiaVähim(juur.p);
            //kustutame parema haru vähima elemendi
            juur.p = eemaldaMinKirje(juur.p, paremaVähim);
            //asendame käesoleva kustutatava juurtipu sisu paremalt leitud vähimaga
            juur.väärtus = paremaVähim;
        }

        //uuendame kõrgused ja tasakaalustame
        uuendaKõrgus(juur);
        return tasakaalusta(juur);
    }

    /**
     * Meetod eemaldab minimaalse kirje (vajalik parema haru vähima elemendi kustutamiseks)
     * @param juur vastava puu juur
     * @param väärtus kustutatav väärtus
     * @return uue puu juur
     */
    public static KOTipp eemaldaMinKirje(KOTipp juur, int väärtus) {
        if (juur == null) {
            return null;
        } else if (väärtus < juur.väärtus) {
            juur.v = eemaldaMinKirje(juur.v, väärtus);
        } else if (väärtus > juur.väärtus) {
            juur.p = eemaldaMinKirje(juur.p, väärtus);
        } else {
            if (juur.v == null && juur.p == null) {
                return null;
            } else if (juur.v == null) return juur.p;
            else if (juur.p == null) return juur.v;
            //kui tipul on kaks haru, siis see tähendab, et tegemist on dublikaadiga ja peame otsima edasi
            else{
                juur.v = eemaldaMinKirje(juur.v, väärtus);
            }
        }
        //uuendame kõrgused ja tasakaalustame
        uuendaKõrgus(juur);
        return tasakaalusta(juur);
    }

    /**
     * Meetod leiab puu vähima tipu
     * @param juur vastava puu juur
     * @return vähim leht
     */
    private static int leiaVähim(KOTipp juur) {
        if (juur.v == null) return juur.väärtus;
        return leiaVähim(juur.v);
    }

    /**
     * Meetod põimib kahte sorteeritud järjendi
     * @param puu1 esimese puu väärtused
     * @param puu2 teise puu väärtused
     * @return sorteeritud põimitud järjend
     */
    public static int[] põimi(int[] puu1, int[] puu2) {
        int puu1Pikkus = puu1.length;
        int puu2Pikkus = puu2.length;

        int[] põimitud = new int[puu1Pikkus + puu2Pikkus];

        int puu1Indeks = 0;
        int puu2Indeks = 0;
        int põimitudIndkes = 0;

        //võrdleme mõlema puu väärtused ja lisame enne väiksemad
        while (puu1Indeks < puu1Pikkus && puu2Indeks < puu2Pikkus){
            if (puu1[puu1Indeks] <= puu2[puu2Indeks]){
                põimitud[põimitudIndkes++] = puu1[puu1Indeks++];
            } else{
                põimitud[põimitudIndkes++] = puu2[puu2Indeks++];
            }
        }

        //lisame ülejäänud elemndid
        while (puu1Indeks < puu1Pikkus){
            põimitud[põimitudIndkes++] = puu1[puu1Indeks++];
        }

        //lisame ülejäänud elemndid
        while (puu2Indeks < puu2Pikkus){
            põimitud[põimitudIndkes++] = puu2[puu2Indeks++];
        }

        return põimitud;
    }


    /**
     * Meetod salvestab massiivi puu tippe kasvavas järjekorras
     * @param juur vastava puu juur
     * @return puu tippude massiiv
     */
    public static int[] salvestaMassiivi(KOTipp juur) {
        List<Integer> tulemus = new ArrayList<>();
        läbiKeskjärjestuses(juur, tulemus);

        int[] tulemusInt = new int[tulemus.size()];
        for (int i = 0; i < tulemus.size(); i++) {
            tulemusInt[i] = tulemus.get(i);
        }

        return tulemusInt;
    }

    /**
     * Meetod läbib puu tippe keskjärjestuses ja salvestab nende väärtused järjendisse
     * @param juur vastava puu juur
     * @param tulemus puu tippude järjend
     */
    private static void läbiKeskjärjestuses(KOTipp juur, List<Integer> tulemus) {
        if (juur != null) {
            läbiKeskjärjestuses(juur.v, tulemus);
            tulemus.add(juur.väärtus);
            läbiKeskjärjestuses(juur.p, tulemus);
        }
    }


    /**
     * Meetod liidab kahte puud kokku
     * @param avl1 esimene puu
     * @param avl2 teine puu
     * @return põimitud puu juur
     */
    public static KOTipp liidaAVLpuud(KOTipp avl1, KOTipp avl2) {
        //salvestame mõlema puu väärtused massiividesse
        int[] puu1 = salvestaMassiivi(avl1);
        int[] puu2 = salvestaMassiivi(avl2);

        //põimime mõlemad massiivid kokku
        int[] põimitud = põimi(puu1, puu2);

        return konstrueeriAVLpuu(põimitud, 0, põimitud.length - 1);
    }

    /**
     * Meetod konstrueerib AVL puud
     * @param tippud vastava puu tippud
     * @param alustus esimese tippu indeks
     * @param lõpp viimase tippu indeks
     * @return konstrueeritud puu juur
     */
    private static KOTipp konstrueeriAVLpuu(int[] tippud, int alustus, int lõpp) {
        if (alustus > lõpp) {
            return null;
        }

        int keskpunkt = (alustus + lõpp) / 2;
        KOTipp juur = new KOTipp(tippud[keskpunkt]);

        juur.v = konstrueeriAVLpuu(tippud, alustus, keskpunkt - 1);
        juur.p = konstrueeriAVLpuu(tippud, keskpunkt + 1, lõpp);

        uuendaKõrgus(juur);
        return tasakaalusta(juur);
    }


    /**
     * Meetod uuendab puu kõrgust
     * @param juur vastava puu juur
     */
    public static void uuendaKõrgus(KOTipp juur) {
        juur.x = 1 + Math.max(kõrgus(juur.v), kõrgus(juur.p));
    }


    /**
     * Meetod tagastab vastava puu kõrguse
     * @param juur vastav puu
     * @return puu kõrgus
     */
    public static int kõrgus(KOTipp juur) {
        if (juur == null) {
            return -1;
        } else {
            return juur.x;
        }
    }

    /**
     * Meetod kontrollib puu tasakaalu
     * @param juur vastava puu juur
     * @return vasaku ja parema haru kõrguste vahe
     */
    public static int kontrolliTasakaal(KOTipp juur) {
        return kõrgus(juur.v) - kõrgus(juur.p);
    }


    /**
     * Meetod teeb vasakpöörde
     * @param juur vastava puu juur
     * @return uue puu tipp
     */
    public static KOTipp vasakPööre(KOTipp juur) {
        KOTipp parem = juur.p;
        KOTipp paremVasak = parem.v;

        parem.v = juur;
        juur.p = paremVasak;

        uuendaKõrgus(juur);
        uuendaKõrgus(parem);
        return parem;

    }


    /**
     * Meetod teeb parempöörde
     * @param juur vastava puu juur
     * @return uue puu tipp
     */
    public static KOTipp paremPööre(KOTipp juur) {
        KOTipp vasak = juur.v;
        KOTipp vasakuParem = vasak.p;

        vasak.p = juur;
        juur.v = vasakuParem;

        uuendaKõrgus(juur);
        uuendaKõrgus(vasak);
        return vasak;

    }


    /**
     * Meetod tasakaalustab puu
     * @param juur vastava puu juur
     * @return tasakaalustud puu juur
     */
    public static KOTipp tasakaalusta(KOTipp juur) {
        int tasakaal = kontrolliTasakaal(juur);
        //tipu vasaku ja parema alampuu kõrguste vahe on −2,
        if (tasakaal < -1) {
           //juurtipu vasaku ja parema alampuu kõrguste vahe −1 või 0
            if (kontrolliTasakaal(juur.p) <= 0) {
                //sooritame vasakpööre
                juur = vasakPööre(juur);
              //juurtipu vasaku ja parema alampuu kõrguste vahe +1
            } else {
                //sooritame paremvasakpööre
                juur.p = paremPööre(juur.p);
                juur = vasakPööre(juur);
            }
        }

        //tipu vasaku ja parema alampuu kõrguste vahe on −2,
       else if (tasakaal > 1) {
            //juurtipu vasaku ja parema alampuu kõrguste vahe +1 või 0
            if (kontrolliTasakaal(juur.v) >= 0) {
                //sooritame parempööre
                juur = paremPööre(juur);
            //juurtipu vasaku ja parema alampuu kõrguste vahe -1
            } else {
                //sooritame vasakparempööre
                juur.v = vasakPööre(juur.v);
                juur = paremPööre(juur);
            }
        }

        return juur;
    }


    public static void main(String[] args) {

    }


}
