package data;

public class Summa {
    private static String[] arvud = {"üks", "kaks", "kolm", "neli", "viis", "kuus", "seitse", "kaheksa", "üheksa"};
    private static String[] sendid = {" sent", " senti"};
    private static String[] eurod = {" euro", " eurot"};
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

    private static String tõlgiSoneks(double arv) {
        String tõlgitav = String.valueOf(arv);
        String cents = "";
        if (tõlgitav.contains(".")) {
            String[] osad = tõlgitav.split("\\.");
            tõlgitav = osad[0];
            cents = " ja " + osad[1] + (osad[1].equals("01") ? sendid[0] : sendid[1]);
        }
        return tõlgitav + (tõlgitav.equals("1") ? eurod[0] : eurod[1]) + cents;
    }
}
