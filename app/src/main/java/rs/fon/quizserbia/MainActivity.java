package rs.fon.quizserbia;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

import rs.fon.quizserbia.BroadcastReciever.AlarmaReciever;
import rs.fon.quizserbia.Common.Common;
import rs.fon.quizserbia.Model.User;

public class MainActivity extends AppCompatActivity {
MaterialEditText edtNewUser,edtNewPassword,edtNewEmail; // registracija
    MaterialEditText edtUser,edtPassword; // logovanje
    Button btnLogovanje,btnRegistracija;
    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");
        edtUser=(MaterialEditText) findViewById(R.id.edtUser);
        edtPassword=(MaterialEditText) findViewById(R.id.edtPassword);
        btnLogovanje=(Button) findViewById(R.id.btn_ulogujeSe);
        btnRegistracija=(Button) findViewById(R.id.btn_registrujSe);
        btnRegistracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prikaziDijalogRegistrovanja();
            }


        });
        btnLogovanje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logovanje(edtUser.getText().toString(),edtPassword.getText().toString());
            }
        });
        registrujAlarm();
    }

    private void registrujAlarm() {

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Intent intent=new Intent(getApplicationContext(), AlarmaReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am=(AlarmManager)getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    private void logovanje(final String user, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(user).exists()){
                    if(!user.isEmpty()){
                        User login=dataSnapshot.child(user).getValue(User.class);
                        if(login.getPassword().equals(pwd))
                        {
                            Intent homeActivity=new Intent(MainActivity.this, Home.class);
                            Common.trenutnoUlogovani=login;
                            startActivity(homeActivity);
                            finish();

                        }

                        else Toast.makeText(MainActivity.this, "Pogresna sifra", Toast.LENGTH_SHORT).show();

                    }
                else{
                        Toast.makeText(MainActivity.this, "Unesite svoje korisnicko ime", Toast.LENGTH_SHORT).show();

                    }

                }
                else
                    Toast.makeText(MainActivity.this, "Korisnik ne postoji", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void   prikaziDijalogRegistrovanja() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Registracija");
        alertDialog.setMessage("Molimo Vas popunite informacije");

        LayoutInflater inflater=this.getLayoutInflater();
        View sign_up_layout=inflater.inflate(R.layout.sign_up_layout,null);

        edtNewUser=(MaterialEditText) sign_up_layout.findViewById(R.id.edtNewUserName);
        edtNewPassword=(MaterialEditText) sign_up_layout.findViewById(R.id.edtNewPassword);
        edtNewEmail=(MaterialEditText) sign_up_layout.findViewById(R.id.edtNewEmail);

        alertDialog.setView(sign_up_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);

        alertDialog.setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final User user=new User(edtNewUser.getText().toString(), edtNewPassword.getText().toString(),edtNewEmail.getText().toString());

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUserName()).exists()){
                            Toast.makeText(MainActivity.this,"Korisnik vec postoji!",Toast.LENGTH_SHORT).show();

                        }
                    else{
                            users.child(user.getUserName()).setValue(user);
                            Toast.makeText(MainActivity.this,"Uspesna registracija",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Odbaci", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
