package rs.fon.quizserbia.Model;

/**
 * Created by Comp on 6/30/2018.
 */

public class Kategorija {
    private String Naziv;
    private String Slika;

    public Kategorija() {

    }

    public Kategorija(String naziv, String slika) {
        Naziv = naziv;
        Slika = slika;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public String getSlika() {
        return Slika;
    }

    public void setSlika(String slika) {
        Slika = slika;
    }
}
