package rs.fon.quizserbia.Model;

/**
 * Created by Comp on 7/1/2018.
 */

public class Pitanje {
    private String Pitanje,OdgovorA,OdgovorB,OdgovorC,OdgovorD,TacanOdgovor,kategorijaID,pitanjeSaSlikom;
    public Pitanje(){}

    public Pitanje(String pitanje, String odgovorA, String odgovorB, String odgovorC, String odgovorD, String tacanOdgovor, String kategorijaID, String pitanjeSaSlikom) {
        Pitanje = pitanje;
        OdgovorA = odgovorA;
        OdgovorB = odgovorB;
        OdgovorC = odgovorC;
        OdgovorD = odgovorD;
        TacanOdgovor = tacanOdgovor;
        this.kategorijaID = kategorijaID;
        this.pitanjeSaSlikom = pitanjeSaSlikom;
    }

    public String getPitanje() {
        return Pitanje;
    }

    public void setPitanje(String pitanje) {
        Pitanje = pitanje;
    }

    public String getOdgovorA() {
        return OdgovorA;
    }

    public void setOdgovorA(String odgovorA) {
        OdgovorA = odgovorA;
    }

    public String getOdgovorB() {
        return OdgovorB;
    }

    public void setOdgovorB(String odgovorB) {
        OdgovorB = odgovorB;
    }

    public String getOdgovorC() {
        return OdgovorC;
    }

    public void setOdgovorC(String odgovorC) {
        OdgovorC = odgovorC;
    }

    public String getOdgovorD() {
        return OdgovorD;
    }

    public void setOdgovorD(String odgovorD) {
        OdgovorD = odgovorD;
    }

    public String getTacanOdgovor() {
        return TacanOdgovor;
    }

    public void setTacanOdgovor(String tacanOdgovor) {
        TacanOdgovor = tacanOdgovor;
    }

    public String getKategorijaID() {
        return kategorijaID;
    }

    public void setKategorijaID(String kategorijaID) {
        this.kategorijaID = kategorijaID;
    }

    public String getPitanjeSaSlikom() {
        return pitanjeSaSlikom;
    }

    public void setPitanjeSaSlikom(String pitanjeSaSlikom) {
        this.pitanjeSaSlikom = pitanjeSaSlikom;
    }
}
