package com.hyedesign.onedic.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyedesign.onedic.R;
import com.hyedesign.onedic.SynonymActivity;
import com.hyedesign.onedic.db.SynonymDataSource;
import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.Synonym;
import com.hyedesign.onedic.utilities.App;
import com.hyedesign.onedic.utilities.FontResets;

import java.util.List;

/**
 * Created by Albaloo on 2/23/2017.
 */

public class SynonymAdapter extends RecyclerView.Adapter<SynonymAdapter.MyViewHolder> {
    public IDataSource<Synonym> dataSource;
    private List<Synonym> synonyms;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, translation, synonym, synonymTranslation;
        public ImageButton play,favorite , edit;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvSynonymTitle);
            translation = (TextView)view.findViewById(R.id.tvSynonymTitleTranslation);
            synonym = (TextView) view.findViewById(R.id.tvSynonym);
            synonymTranslation = (TextView) view.findViewById(R.id.tvSynonymTranslation);

            play = (ImageButton)view.findViewById(R.id.ibtnSynonymPlay);
            favorite = (ImageButton)view.findViewById(R.id.ibtnSynonymFavorite);
            edit = (ImageButton)view.findViewById(R.id.ibtnSynonymEdit);
        }
    }


    public SynonymAdapter(List<Synonym> synonyms) {
        this.synonyms = synonyms;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.synonym_item_row, parent, false);

        FontResets.setFont(parent.getContext(), parent);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Synonym model = synonyms.get(position);
        holder.title.setText(model.getTitle());
        holder.translation.setText(model.getTranslation());
        holder.synonym.setText(model.getSynonym());
        holder.synonymTranslation.setText(model.getSynonymTranslation());

        if(model.getFavorite())
            holder.favorite.setImageResource(R.drawable.ic_star_on);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource = new SynonymDataSource(v.getContext());

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
                Intent intent = new Intent(v.getContext(), SynonymActivity.class);
                intent.putExtra("id",model.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return synonyms.size();
    }

}
