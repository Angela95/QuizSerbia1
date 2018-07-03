package rs.fon.quizserbia.Model;

/**
 * Created by Comp on 7/1/2018.
 */

public class PitanjaRezultat {
    private String Pitanja_Rezultat;
    private String user;
    private String rezultat;
    private String kategorijaID;
    private String naziv;
    public PitanjaRezultat(){}


    public String getPitanja_Rezultat() {
        return Pitanja_Rezultat;
    }

    public void setPitanja_Rezultat(String pitanja_Rezultat) {
        Pitanja_Rezultat = pitanja_Rezultat;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRezultat() {
        return rezultat;
    }

    public void setRezultat(String rezultat) {
        this.rezultat = rezultat;
    }

    public String getKategorijaID() {
        return kategorijaID;
    }

    public void setKategorijaID(String kategorijaID) {
        this.kategorijaID = kategorijaID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public PitanjaRezultat(String pitanja_Rezultat, String user, String rezultat, String kategorijaID, String naziv) {

        Pitanja_Rezultat = pitanja_Rezultat;
        this.user = user;
        this.rezultat = rezultat;
        this.kategorijaID = kategorijaID;
        this.naziv = naziv;
    }
}
