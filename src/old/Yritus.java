package old;

import java.util.ArrayList;

public class Yritus {
    private String nimi;
    private ArrayList<Pilet> piletid;
    private double hetkeSumma;

    public Yritus(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }

    public ArrayList<Pilet> getPiletid() {
        return piletid;
    }

    public double getHetkeSumma() {
        return hetkeSumma;
    }

    public void lisaPilet(String nimi, double hind) {
        piletid.add(new Pilet(nimi, hind));
    }

    public void kustutaPilet(Pilet p) {
        piletid.remove(p);
    }

    public double arvutaHetkeSumma() {
        double summa = 0;
        for (Pilet p : piletid) {
            summa += p.getHind() * p.getHetkekogus();
        }
        hetkeSumma = summa;
        return summa;
    }

    public void test(){

    }
    //test
}
