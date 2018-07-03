package rs.fon.quizserbia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rs.fon.quizserbia.Common.Common;
import rs.fon.quizserbia.Model.PitanjaRezultat;

public class Done extends AppCompatActivity {
Button pokusajPonovo;
TextView txtRezultat,getTxtRezultatPitanja;
ProgressBar progressBar;

FirebaseDatabase database;
DatabaseReference rezultat_pitanja;
ProgressDialog nDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
    database=FirebaseDatabase.getInstance();
    rezultat_pitanja=database.getReference("Pitanja_Rezultat");

    txtRezultat=(TextView)findViewById(R.id.txtTotalScore);
    getTxtRezultatPitanja=(TextView)findViewById(R.id.txtTotalQuestion);
    progressBar=(ProgressBar)findViewById(R.id.doneProgressBar);
    pokusajPonovo=(Button)findViewById(R.id.btnTryAgain);

    nDialog=new ProgressDialog(Done.this);
    pokusajPonovo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            nDialog.setMessage("Učitavam ponovo");
            nDialog.setTitle("Ažuriram podatke");
            nDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            nDialog.setCancelable(false);
            nDialog.show();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    nDialog.dismiss();
                }
            }).start();

            Intent intent=new Intent(Done.this,Home.class);

            startActivity(intent);

            finish();
        }
    });
    Bundle extra=getIntent().getExtras();
    if(extra!=null){
        int rezultat=extra.getInt("REZULTAT");
        int totalPitanja=extra.getInt("UKUPNO");
        int tacanOdgovor=extra.getInt("TAČNIH");

        txtRezultat.setText(String.format("REZULTAT: %d",rezultat));
        getTxtRezultatPitanja.setText(String.format("TAČNIH: %d / %d",tacanOdgovor,totalPitanja ));
        progressBar.setMax(totalPitanja);
        progressBar.setProgress(tacanOdgovor);

        //upload u bazu
        rezultat_pitanja.child(String.format("%s_%s", Common.trenutnoUlogovani.getUserName(),
                Common.kategorijaID))
                .setValue(new PitanjaRezultat(String.format("%s_%s",Common.trenutnoUlogovani.getUserName(),
                        Common.kategorijaID),
                        Common.trenutnoUlogovani.getUserName(),
                        String.valueOf(rezultat),
                        Common.kategorijaID,
                        Common.naziv));

    }
    }
}
