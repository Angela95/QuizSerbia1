package rs.fon.quizserbia.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rs.fon.quizserbia.Interfaces.ItemClickListener;
import rs.fon.quizserbia.R;

/**
 * Created by Comp on 7/1/2018.
 */

public class RankingViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_name,txt_score;
    private ItemClickListener itemClickListener;

    public RankingViewModel(View itemView){
        super(itemView);
        txt_name=(TextView)itemView.findViewById(R.id.txt_name);
        txt_score=(TextView)itemView.findViewById(R.id.txt_score);

        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
