package rs.fon.quizserbia.Model;

/**
 * Created by Comp on 7/1/2018.
 */

public class Rangiranje {
    private String userName;
    private long rezultat;
public  Rangiranje(){}

    public Rangiranje(String userName, long rezultat) {
        this.userName = userName;
        this.rezultat = rezultat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getRezultat() {
        return rezultat;
    }

    public void setRezultat(long rezultat) {
        this.rezultat = rezultat;
    }
}
