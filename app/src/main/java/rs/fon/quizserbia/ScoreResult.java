package rs.fon.quizserbia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import rs.fon.quizserbia.Model.PitanjaRezultat;
import rs.fon.quizserbia.ViewHolder.ScoreDetailViewHolder;

public class ScoreResult extends AppCompatActivity {
FirebaseDatabase database;
DatabaseReference pitanja_rezultat;

RecyclerView rangLista;
RecyclerView.LayoutManager layoutManager;
FirebaseRecyclerAdapter<PitanjaRezultat,ScoreDetailViewHolder> adapter;

String viewUser="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_result);
        database=FirebaseDatabase.getInstance();
        pitanja_rezultat=database.getReference("Pitanja_Rezultat");

        rangLista=(RecyclerView)findViewById(R.id.scoreList);
        rangLista.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        rangLista.setLayoutManager(layoutManager);

        if(getIntent()!=null){
            viewUser=getIntent().getStringExtra("viewUser");
            if(!viewUser.isEmpty()){
                ucitajRezultate(viewUser);

            }

        }
    }

    private void ucitajRezultate(String viewUser) {
        adapter=new FirebaseRecyclerAdapter<PitanjaRezultat, ScoreDetailViewHolder>(
                PitanjaRezultat.class,
                R.layout.score_detail_layout,
                ScoreDetailViewHolder.class,
                pitanja_rezultat.orderByChild("user").equalTo(viewUser)
        ) {
            @Override
            protected void populateViewHolder(ScoreDetailViewHolder viewHolder, PitanjaRezultat model, int position) {
                viewHolder.txt_category.setText(model.getNaziv());
                viewHolder.txt_score.setText(model.getRezultat());
            }
        };
        adapter.notifyDataSetChanged();
        rangLista.setAdapter(adapter);
    }
}
