package rs.fon.quizserbia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import rs.fon.quizserbia.Common.Common;
import rs.fon.quizserbia.Model.Pitanje;

import static rs.fon.quizserbia.Common.Common.listaPitanja;

public class StartActivity extends AppCompatActivity {
Button btnPlay;

FirebaseDatabase database;
DatabaseReference pitanja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
   database=FirebaseDatabase.getInstance();
   pitanja=database.getReference("Pitanja");
   ucitajPitanja(Common.kategorijaID);
    btnPlay=(Button)findViewById(R.id.btnPlay);

    btnPlay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(StartActivity.this,Playing.class);
            startActivity(intent);
            finish();
        }
    });
    }

    private void ucitajPitanja(String kategorijaID) {
        if (listaPitanja.size() > 0)
            listaPitanja.clear();

        pitanja.orderByChild("kategorijaID").equalTo(kategorijaID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Pitanje p = postSnapshot.getValue(Pitanje.class);
                            listaPitanja.add(p);

                        }
                        Collections.shuffle(listaPitanja,new Random());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });

    }


    }


