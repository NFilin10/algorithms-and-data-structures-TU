public class NimedegaNaabrusmaatriks {
    final int[][] M;
    final String[] nimed;

    NimedegaNaabrusmaatriks(int[][] M, String[] nimed) {
        this.M = M;
        this.nimed = nimed;
    }

    public NimedegaNaabrusmaatriks kopeeri() {
        String[] koopiaNimed = new String[nimed.length];
        for (int i = 0; i < nimed.length; i++) koopiaNimed[i] = nimed[i];
        int[][] koopiaM = new int[nimed.length][nimed.length];
        for (int rida = 0; rida < nimed.length; rida++)
            for (int veerg = 0; veerg < nimed.length; veerg++)
                koopiaM[rida][veerg] = M[rida][veerg];
        return new NimedegaNaabrusmaatriks(koopiaM, koopiaNimed);
    }

    @Override
    public String toString() {
        int[] veerud = new int[nimed.length + 1];
        for (int veerg = 0; veerg < veerud.length; veerg++) veerud[veerg] = 4;
        veerud[0] = 3;
        for (String nimi : nimed) if (nimi.length() > veerud[0]) veerud[0] = nimi.length();
        for (int veerg = 0; veerg < nimed.length; veerg++)
            if (nimed[veerg].length() > veerud[veerg + 1]) veerud[veerg + 1] = nimed[veerg].length();
        for (int veerg = 0; veerg < nimed.length; veerg++)
            for (int rida = 0; rida < nimed.length; rida++)
                if (Integer.toString(M[rida][veerg]).length() > veerud[veerg + 1])
                    veerud[veerg + 1] = Integer.toString(M[rida][veerg]).length();
        StringBuilder sisu = new StringBuilder();
        sisu.append(" ".repeat(veerud[0] + 1));
        for (int i = 0; i < nimed.length; i++) {
            sisu.append(nimed[i]);
            sisu.append(" ".repeat(veerud[i + 1] - nimed[i].length() + 1));
        }
        for (int rida = 0; rida < nimed.length; rida++) {
            sisu.append("\n" + nimed[rida] + " ".repeat(veerud[0] - nimed[rida].length() + 1));
            for (int veerg = 0; veerg < nimed.length; veerg++)
                if (M[rida][veerg] != -1) {
                    int tühjus = veerud[veerg + 1] - Integer.toString(M[rida][veerg]).length() + 1;
                    sisu.append(M[rida][veerg] + " ".repeat(tühjus));
                } else sisu.append("∞" + " ".repeat(veerud[veerg + 1]));
        }
        return sisu.toString();
    }
}
