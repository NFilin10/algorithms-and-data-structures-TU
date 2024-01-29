/*****************************************************************************
 * Algoritmid ja andmestruktuurid. LTAT.03.005
 * 2023/2024 sügissemester
 *
 * Kodutöö. Ülesanne nr 2A
 * Teema: Rekursioon
 *
 * Autor: Nikita Filin
 *
 *****************************************************************************/

public class Kodu2A {
    /**
     * Meetod kutsub välja rekursiivset abi meetodid.
     * @param a eseme kaalude järjend
     * @return tõeväärtus kas sellest järjendist mingi elementide valik, milles on mitte rohkem kui pooled elemendid, annab kogukaaluks 10 kg
     */
    public static boolean valik(int[] a) {
        return abi(a, 0, 0, 0);
    }

    /**
     * Rekursiivne meetod, mis leiab kas sellest järjendist mingi elementide valik, milles on mitte rohkem kui pooled elemendid, annab kogukaaluks 10 kg
     * @param a eseme kaalude järjend
     * @param sum jooksev summa
     * @param i järjendi indeks
     * @param pikkus jooksev pikkus
     * @return tõeväärtus kas leidub selline elementide valik
     */
    public static boolean abi(int[] a, int sum, int i, int pikkus){
        //baas
        if (sum == 10000 && pikkus <= a.length / 2){
            return true;
        }

        //baas
        if (i == a.length || sum > 10000){
            return false;
        }

        //otsustame kas valime või ei vali elemendi
        return abi(a, sum+a[i], i+1, pikkus+1) || abi(a, sum, i+1, pikkus);
    }


    public static void main(String[] args) {
        int[] a = {100, 100, 400, 200, 201, 200, 1000, 1, 8000, 2, 2, 2,2,2,};
        System.out.println(valik(a));
    }
}