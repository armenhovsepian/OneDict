package com.hyedesign.onedic.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hyedesign.onedic.IdiomActivity;
import com.hyedesign.onedic.R;
import com.hyedesign.onedic.db.IdiomDataSource;
import com.hyedesign.onedic.models.Idiom;
import com.hyedesign.onedic.utilities.App;
import com.hyedesign.onedic.utilities.FontResets;

import java.util.List;

/**
 * Created by Albaloo on 2/24/2017.
 */

public class IdiomAdapter extends RecyclerView.Adapter<IdiomAdapter.MyViewHolder> {

    IdiomDataSource dataSource;
    List<Idiom> idioms;

    public IdiomAdapter(List<Idiom> idioms){
        this.idioms = idioms;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, translation;
        public ImageButton play,favorite , edit;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvIdiomTitle);
            translation = (TextView) view.findViewById(R.id.tvIdiomTranslation);

            play =(ImageButton)view.findViewById(R.id.ibtnIdiomPlay);
            favorite = (ImageButton)view.findViewById(R.id.ibtnIdiomFavorite);
            edit =(ImageButton)view.findViewById(R.id.ibtnIdiomEdit);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.idiom_item_row, parent, false);

        FontResets.setFont(parent.getContext(), parent);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Idiom model = idioms.get(position);
        holder.title.setText(model.getTitle());
        holder.translation.setText(model.getTranslation());

        if(model.getFavorite())
            holder.favorite.setImageResource(R.drawable.ic_star_on);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource = new IdiomDataSource(v.getContext());

                if (model.getFavorite()){
                    dataSource.favorite(model.getId(),"0");
                    model.setFavorite(0);
                    holder.favorite.setImageResource(R.drawable.ic_star_off);
                    App.toast(R.string.removedFromFavorites);
                }else{
                    dataSource.favorite(model.getId(),"1");
                    model.setFavorite(1);
                    holder.favorite.setImageResource(R.drawable.ic_star_on);
                    App.toast(R.string.addToFavorites);
                }

                notifyDataSetChanged();
                notifyItemChanged(position);
            }
        });


        holder.play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String toSpeak = model.getTitle();
                App.textToSpeech(toSpeak);
            }

        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), IdiomActivity.class);
                intent.putExtra("id",model.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idioms.size();
    }

}
