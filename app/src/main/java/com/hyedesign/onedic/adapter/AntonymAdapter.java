package com.hyedesign.onedic.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyedesign.onedic.AntonymActivity;
import com.hyedesign.onedic.R;
import com.hyedesign.onedic.db.AntonymDataSource;
import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.Antonym;
import com.hyedesign.onedic.utilities.App;
import com.hyedesign.onedic.utilities.FontResets;

import java.util.List;

/**
 * Created by Albaloo on 2/23/2017.
 */

public class AntonymAdapter extends RecyclerView.Adapter<AntonymAdapter.MyViewHolder> {
    public IDataSource<Antonym> dataSource;
    private List<Antonym> antonyms;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, translation, antonym, antonymTranslation;
        public ImageButton play, favorite , edit;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvAntonymTitle);
            translation = (TextView)view.findViewById(R.id.tvAntonymTitleTranslation);
            antonym = (TextView) view.findViewById(R.id.tvAntonym);
            antonymTranslation = (TextView)view.findViewById(R.id.tvAntonymTranslation);

            play = (ImageButton)view.findViewById(R.id.ibtnAntonymPlay);
            favorite = (ImageButton)view.findViewById(R.id.ibtnAntonymFavorite);
            edit = (ImageButton)view.findViewById(R.id.ibtnAntonymEdit);
        }
    }


    public AntonymAdapter(List<Antonym> antonyms) {
        this.antonyms = antonyms;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.antonym_item_row, parent, false);

        FontResets.setFont(parent.getContext(), parent);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Antonym model = antonyms.get(position);
        holder.title.setText(model.getTitle());
        holder.translation.setText(model.getTranslation());
        holder.antonym.setText(model.getAntonym());
        holder.antonymTranslation.setText(model.getAntonymTranslation());


        if(model.getFavorite())
            holder.favorite.setImageResource(R.drawable.ic_star_on);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource = new AntonymDataSource(v.getContext());

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
                Intent intent = new Intent(v.getContext(), AntonymActivity.class);
                intent.putExtra("id",model.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return antonyms.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
/*        tts = new TextToSpeech(recyclerView.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    tts.setLanguage(Locale.ENGLISH);
                }else {

                }
            }
        });*/
    }
}
