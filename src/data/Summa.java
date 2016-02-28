package data;

import java.util.Arrays;
import java.util.Collections;

public class Summa {
    private static String[] arvud = {"", "üks", "kaks", "kolm", "neli", "viis", "kuus", "seitse", "kaheksa", "üheksa"};
    private static String[] sendiühik = {" sent", " senti"};
    private static String[] euroühik = {"euro", "eurot"};
    private double arv;
    private String arvSonena;

    public Summa(double arv) {
        this.setArv(arv);
    }

    public String toString() {
        return this.arvSonena;
    }

    public double getArv() {
        return arv;
    }

    public void setArv(double arv) {
        //todo smth?
        this.arv = arv;
        this.arvSonena = tõlgiSoneks(arv);
    }

    private static String tõlgiEurod(String täisosa) { //todo debug - tühikuid on liiga palju kohati
        StringBuilder tagurpidi = new StringBuilder(täisosa).reverse();
        StringBuilder tõlgitud = new StringBuilder(täisosa.length());
        String[] sõnad = new String[täisosa.length()];
        for (int i = 0; i < täisosa.length(); i++) {
            int hetkearv = Character.getNumericValue(tagurpidi.charAt(i));
            if (i == 0 || hetkearv == 0) {
                sõnad[i] = arvud[hetkearv] + " ";
            } else if (i == 1) { //12
                if (hetkearv == 1) {
                    if (sõnad[0].equals(" ")) {
                        sõnad[i] = "kümme ";
                    } else {
                        sõnad[i] = arvud[Character.getNumericValue(tagurpidi.charAt(0))] + "teist ";
                    }
                    sõnad[0] = "";
                } else if (hetkearv > 1) {
                    sõnad[i] = arvud[hetkearv] + "kümmend ";
                }
            } else if (i == 2) { //123
                sõnad[i] = arvud[hetkearv] + "sada ";
            } else if (i == 3 && Character.getNumericValue(tagurpidi.charAt(4)) != 0) { //1234
                if (hetkearv == 1) {
                    sõnad[i] = "üks tuhat ";
                } else {
                    sõnad[i] = arvud[hetkearv] + " tuhat ";
                }
            } else if (i == 4) { //12345
                if (hetkearv == 1) {
                    if (sõnad[3].equals(" ")) {
                        sõnad[i] = "kümme tuhat ";
                    } else {
                        sõnad[i] = arvud[Character.getNumericValue(tagurpidi.charAt(3))] + "teist ";
                        sõnad[3] = "tuhat ";
                    }
                } else {
                    sõnad[i] = arvud[hetkearv] + "kümmend ";
                }
            } else {
                sõnad[i] = arvud[hetkearv] + " ";
            }
        }
        Collections.reverse(Arrays.asList(sõnad));
        for (String s : sõnad) {
            tõlgitud.append(s);
        }
        return tõlgitud.toString();
    }

    private static String tõlgiSoneks(double arv) {
        String tõlgitav = String.valueOf(arv);
        String[] osad = tõlgitav.split("\\.");
        tõlgitav = osad[0];
        String sendid = osad[1].equals("0") ? "" : (" ja " + osad[1] + (osad[1].equals("01") ? sendiühik[0] : sendiühik[1]));
        tõlgitav = tõlgiEurod(tõlgitav);
        return tõlgitav + (tõlgitav.equals("üks ") ? euroühik[0] : euroühik[1]) + sendid;
    }
}
