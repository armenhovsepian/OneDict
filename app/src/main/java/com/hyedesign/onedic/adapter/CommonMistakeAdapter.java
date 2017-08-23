package com.hyedesign.onedic.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyedesign.onedic.CommonMistakeActivity;
import com.hyedesign.onedic.R;
import com.hyedesign.onedic.db.CommonMistakeDataSource;
import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.CommonMistake;
import com.hyedesign.onedic.utilities.App;
import com.hyedesign.onedic.utilities.FontResets;

import java.util.List;

/**
 * Created by Albaloo on 2/24/2017.
 */

public class CommonMistakeAdapter extends RecyclerView.Adapter<CommonMistakeAdapter.MyViewHolder> {
    public IDataSource<CommonMistake> dataSource;
    private List<CommonMistake> commonMistakes;

    public CommonMistakeAdapter(List<CommonMistake> commonMistakes){
        this.commonMistakes = commonMistakes;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, commonMistake;
        public ImageButton play, favorite , edit;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvCommonMistakeTitle);
            commonMistake = (TextView) view.findViewById(R.id.tvCommonMistake);

            play =(ImageButton)view.findViewById(R.id.ibtnCommonMistakePlay);
            favorite = (ImageButton)view.findViewById(R.id.ibtnCommonMistakeFavorite);
            edit =(ImageButton)view.findViewById(R.id.ibtnCommonMistakeEdit);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commonmistake_item_row, parent, false);

        FontResets.setFont(parent.getContext(), parent);

        return new CommonMistakeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CommonMistake model = commonMistakes.get(position);
        holder.title.setText(model.getTitle());
        holder.commonMistake.setText(model.getCommonMistake());

        if(model.getFavorite())
            holder.favorite.setImageResource(R.drawable.ic_star_on);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource = new CommonMistakeDataSource(v.getContext());

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
                Intent intent = new Intent(v.getContext(), CommonMistakeActivity.class);
                intent.putExtra("id",model.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commonMistakes.size();
    }

}
