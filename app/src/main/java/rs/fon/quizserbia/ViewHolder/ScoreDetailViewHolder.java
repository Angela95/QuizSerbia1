package rs.fon.quizserbia.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rs.fon.quizserbia.R;

/**
 * Created by Comp on 7/1/2018.
 */

public class ScoreDetailViewHolder extends RecyclerView.ViewHolder {
    public TextView txt_category,txt_score;
    public ScoreDetailViewHolder(View itemView) {
        super(itemView);
        txt_category=(TextView)itemView.findViewById(R.id.txt_category);
        txt_score=(TextView)itemView.findViewById(R.id.txt_score);

    }
}
