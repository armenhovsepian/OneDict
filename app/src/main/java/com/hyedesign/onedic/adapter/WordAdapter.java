package com.hyedesign.onedic.adapter;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hyedesign.onedic.R;
import com.hyedesign.onedic.WordActivity;
import com.hyedesign.onedic.db.WordDataSource;
import com.hyedesign.onedic.interfaces.IDataSource;
import com.hyedesign.onedic.models.Word;
import com.hyedesign.onedic.utilities.App;
import com.hyedesign.onedic.utilities.FontResets;


import java.util.List;
import java.util.Locale;

/**
 * Created by Albaloo on 2/20/2017.
 * http://www.androidhive.info/2016/01/android-working-with-recycler-view/
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MyViewHolder> {


    public IDataSource<Word> dataSource;
    //private Context context;
    private List<Word> words;

    //public TextToSpeech tts;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, trans, pron;
        public ImageButton play , favorite , edit;

        public MyViewHolder(View view) {
            super(view);

            //Toast.makeText(view.getContext(),"MyViewHolder",Toast.LENGTH_SHORT).show();

            //context = view.getContext();
            dataSource = new WordDataSource(view.getContext());

            title = (TextView) view.findViewById(R.id.tvWord);
            pron = (TextView) view.findViewById(R.id.tvPro);
            trans = (TextView) view.findViewById(R.id.tvTrans);

            play = (ImageButton) view.findViewById(R.id.ibtnWordPlay);
            favorite = (ImageButton)view.findViewById(R.id.ibtnWordFavorite);
            edit = (ImageButton) view.findViewById(R.id.ibtnWordEdit);

/*            tts = new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        tts.setLanguage(Locale.ENGLISH);
                        //tts.setLanguage(Locale.US);
                    }

                    if (status == TextToSpeech.SUCCESS){

                    }else {

                    }
                }
            });*/
        }
    }


    public WordAdapter(List<Word> words) {
        this.words = words;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_item_row, parent, false);

        FontResets.setFont(parent.getContext(), parent);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Word word = words.get(position);
        holder.title.setText(word.getTitle());

        if(word.getPronunciation() != null || word.getPronunciation() != "")
            holder.pron.setText("/ " + word.getPronunciation() + " /");

        holder.trans.setText(word.getTranslation());

        if(word.getFavorite())
            holder.favorite.setImageResource(R.drawable.ic_star_on);

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dataSource = new WordDataSource(v.getContext());

                if (word.getFavorite()){
                    dataSource.favorite(word.getId(),"0");
                    word.setFavorite(0);
                    holder.favorite.setImageResource(R.drawable.ic_star_off);
                    App.toast(R.string.removedFromFavorites);
                }else{
                    dataSource.favorite(word.getId(),"1");
                    word.setFavorite(1);
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
                String toSpeak = word.getTitle();
                App.textToSpeech(toSpeak);
                //tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
              /*  tts.stop();
                tts.shutdown();*/
            }

        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), WordActivity.class);
                intent.putExtra("id",word.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //Toast.makeText(recyclerView.getContext(),"Attached To RecyclerView",Toast.LENGTH_SHORT).show();

    }

   @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        //Toast.makeText(recyclerView.getContext(),"Detached To RecyclerView",Toast.LENGTH_SHORT).show();
    }



}
