package rs.fon.quizserbia.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import rs.fon.quizserbia.Interfaces.ItemClickListener;
import rs.fon.quizserbia.R;

/**
 * Created by Comp on 6/30/2018.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView imeKategorije;
    public ImageView slikaKategorije;

    private ItemClickListener itemClickListener;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        slikaKategorije = (ImageView) itemView.findViewById(R.id.category_image);
        imeKategorije = (TextView) itemView.findViewById(R.id.category_name);
        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);

    }
}
