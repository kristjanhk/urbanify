package old;

public class Pilet {
    private String nimi;
    private double hind;
    private int kogus = 0;
    private double kasum = 0;
    private int hetkekogus = 0;

    public Pilet(String nimi, double hind) {
        this.nimi = nimi;
        this.hind = hind;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public double getHind() {
        return hind;
    }

    public void setHind(double hind) {
        this.hind = hind;
    }

    //neid kahte vaja?

    public int getKogus() {
        return kogus;
    }

    public void setKogus(int kogus) {
        this.kogus = kogus;
    }

    public double getKoguhind() {
        return kasum;
    }

    public int getHetkekogus() {
        return hetkekogus;
    }

    public void addHetkekogus() {
        this.hetkekogus += 1;
    }

    public void removeHetkekogus() {
        if (this.hetkekogus > 0) {
            this.hetkekogus -= 1;
        }
    }
}