package rs.fon.quizserbia;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.RecoverySystem;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import rs.fon.quizserbia.Common.Common;

public class Playing extends AppCompatActivity implements View.OnClickListener{
    final static long INTERVAL=1000;
    final static long TIMEOUT=7000;
    int progressValue=0;

    CountDownTimer countDownTimer;

    int index=0,score=0,thisPitanje=0,totalPitanja=0,tacanOdgovor=0;



    ProgressBar progressBar;
    ImageView pitanjeSaSlikom;
    Button btnA,btnB,btnC,btnD;
    TextView txtScore,txtQuestionNum,question_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);


        txtScore=(TextView)findViewById(R.id.txtScore);
        txtQuestionNum=(TextView)findViewById(R.id.txtTotalQuestion);
        question_text=(TextView)findViewById(R.id.pitanje_tekst);
        pitanjeSaSlikom=(ImageView)findViewById(R.id.pitanje_slika);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        btnA=(Button)findViewById(R.id.btnOdgovorA);
        btnB=(Button)findViewById(R.id.btnOdgovorB);
        btnC=(Button)findViewById(R.id.btnOdgovorC);
        btnD=(Button)findViewById(R.id.btnOdgovorD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        countDownTimer.cancel();
        if(index<totalPitanja){
            Button clickButton=(Button)v;
            if(clickButton.getText().equals(Common.listaPitanja.get(index).getTacanOdgovor()))
            {
                score+=10;
                tacanOdgovor++;
                prikaziPitanje(++index);

            }
            else
            {
                Intent intent=new Intent(Playing.this,Done.class);
                Bundle dataSend=new Bundle();
                dataSend.putInt("REZULTAT",score);
                dataSend.putInt("UKUPNO",totalPitanja);
                dataSend.putInt("TAČNIH",tacanOdgovor);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();

            }
            txtScore.setText(String.format("%d",score));
        }
    }

    private void prikaziPitanje(int index) {
        if(index<totalPitanja){
            thisPitanje++;
            txtQuestionNum.setText(String.format("%d / %d",thisPitanje,totalPitanja));
            progressBar.setProgress(0);
            progressValue=0;
            if(Common.listaPitanja.get(index).getPitanjeSaSlikom().equals("true"))
            {
                Picasso.with(getBaseContext())
                        .load(Common.listaPitanja.get(index).getPitanje())
                        .into(pitanjeSaSlikom);
                pitanjeSaSlikom.setVisibility(View.VISIBLE);
                question_text.setVisibility(View.INVISIBLE);

            }
            else{
                question_text.setText(Common.listaPitanja.get(index).getPitanje());

                pitanjeSaSlikom.setVisibility(View.INVISIBLE);
                question_text.setVisibility(View.VISIBLE);

            }



            btnA.setText(Common.listaPitanja.get(index).getOdgovorA());
            btnB.setText(Common.listaPitanja.get(index).getOdgovorB());
            btnC.setText(Common.listaPitanja.get(index).getOdgovorC());
            btnD.setText(Common.listaPitanja.get(index).getOdgovorD());

            countDownTimer.start();





        }
        else{
            //ako je zavrsno pitanje
            Intent intent=new Intent(Playing.this,Done.class);
            Bundle dataSend=new Bundle();
            dataSend.putInt("REZULTAT",score);
            dataSend.putInt("UKUPNO",totalPitanja);
            dataSend.putInt("TAČNIH",tacanOdgovor);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalPitanja=Common.listaPitanja.size();
                countDownTimer=new CountDownTimer(TIMEOUT,INTERVAL){
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                prikaziPitanje(++index);
            }
        };
                prikaziPitanje(index);
    }
}
