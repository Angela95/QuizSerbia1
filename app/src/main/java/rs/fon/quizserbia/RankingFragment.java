package rs.fon.quizserbia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import rs.fon.quizserbia.Common.Common;
import rs.fon.quizserbia.Interfaces.ItemClickListener;
import rs.fon.quizserbia.Interfaces.RangiranjaCallBack;
import rs.fon.quizserbia.Model.PitanjaRezultat;
import rs.fon.quizserbia.Model.Rangiranje;
import rs.fon.quizserbia.ViewHolder.RankingViewModel;
import rs.fon.quizserbia.ViewHolder.ScoreDetailViewHolder;


public class RankingFragment extends Fragment {
    View myFragment;
    RecyclerView rangLista;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Rangiranje,RankingViewModel> adapter;

    FirebaseDatabase database;
    DatabaseReference pitanjeRezultat,rangiranjeTabela;
    int suma=0;

    public  static RankingFragment newInstance(){
        RankingFragment rankingFragment=new RankingFragment();
        return  rankingFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        pitanjeRezultat=database.getReference("Pitanja_Rezultat");
        rangiranjeTabela=database.getReference("Rangiranje");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment=inflater.inflate(R.layout.fragment_ranking,container,false);

       rangLista=(RecyclerView)myFragment.findViewById(R.id.rankingList);
       layoutManager=new LinearLayoutManager(getActivity());
       rangLista.setHasFixedSize(true);

       layoutManager.setReverseLayout(true);
       layoutManager.setStackFromEnd(true);
       rangLista.setLayoutManager(layoutManager);





        updateRezultat(Common.trenutnoUlogovani.getUserName(), new RangiranjaCallBack<Rangiranje>() {
            @Override
            public void callBack(Rangiranje rangiranje) {
                rangiranjeTabela.child(rangiranje.getUserName())
                        .setValue(rangiranje);
             //   prikaziRangiranje();
            }
        });
adapter=new FirebaseRecyclerAdapter<Rangiranje, RankingViewModel>(
        Rangiranje.class,
        R.layout.layout_ranking,
        RankingViewModel.class,
        rangiranjeTabela.orderByChild("rezultat")


) {
    @Override
    protected void populateViewHolder(RankingViewModel viewHolder, final Rangiranje model, int position) {
            viewHolder.txt_name.setText(model.getUserName());
            viewHolder.txt_score.setText(String.valueOf(model.getRezultat()));

            viewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent scoreDetail=new Intent(getActivity(), ScoreResult.class);
                    scoreDetail.putExtra("viewUser",model.getUserName());
                    startActivity(scoreDetail);

                }
            });
    }
};
adapter.notifyDataSetChanged();
rangLista.setAdapter(adapter);
        return myFragment;
    }

    /*private void prikaziRangiranje() {
        rangiranjeTabela.orderByChild("rezultat")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            Rangiranje local=data.getValue(Rangiranje.class);;
                            Log.d("DEBUG",local.getUserName());



                        }
                    }
                });

    }*/

    private void updateRezultat(final String userName, final RangiranjaCallBack<Rangiranje> callback) {
   pitanjeRezultat.orderByChild("user").equalTo(userName)
           .addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   for(DataSnapshot data:dataSnapshot.getChildren()){
                       PitanjaRezultat p=data.getValue(PitanjaRezultat.class);
                        suma+=Integer.parseInt(p.getRezultat());
                   }
                   Rangiranje rangiranje=new Rangiranje(userName,suma);
                   callback.callBack(rangiranje);
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });


    }
}
