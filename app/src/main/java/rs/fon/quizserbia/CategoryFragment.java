package rs.fon.quizserbia;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import rs.fon.quizserbia.Common.Common;
import rs.fon.quizserbia.Interfaces.ItemClickListener;
import rs.fon.quizserbia.Model.Kategorija;
import rs.fon.quizserbia.ViewHolder.CategoryViewHolder;


public class CategoryFragment extends Fragment {
    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Kategorija, CategoryViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference kategorije;


    View myFragment;

    public static CategoryFragment newInstance() {
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        kategorije = database.getReference("Kategorije");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        myFragment = inflater.inflate(R.layout.fragment_category, container, false);
        listCategory = (RecyclerView) myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listCategory.setLayoutManager(layoutManager);

        ucitajKategorije();

        return myFragment;
    }

    private void ucitajKategorije() {
        adapter=new FirebaseRecyclerAdapter<Kategorija, CategoryViewHolder>(
                Kategorija.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                kategorije


        ) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Kategorija model, int position) {
                viewHolder.imeKategorije.setText(model.getNaziv());
                Picasso.with(getActivity())
                        .load(model.getSlika())
                        .into(viewHolder.slikaKategorije);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                     Intent start=new Intent(getActivity(),StartActivity.class);
                        Common.kategorijaID=adapter.getRef(position).getKey();
                        Common.naziv=model.getNaziv();
                        startActivity(start);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);
    }
}
