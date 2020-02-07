package com.sharath.moviesearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemRowHolder> {


    MovieAdapter.OnItemClickListener mListener;
    private ArrayList<MovieModel> item_array;
    Context mContext;


    public MovieAdapter(Context mContext, ArrayList<MovieModel> item_array) {
        this.item_array = item_array;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MovieAdapter.ItemRowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list, viewGroup, false);

        MovieAdapter.ItemRowHolder mh = new MovieAdapter.ItemRowHolder(v);
        return mh;
    }


    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.ItemRowHolder itemRowHolder, @SuppressLint("RecyclerView") final int i) {

        final MovieModel MovieModel =item_array.get(i);

        itemRowHolder.title_tv.setText(item_array.get(i).getTitle());

        if(item_array.get(i).getPoster()!=null && !item_array.get(i).getPoster().equals("")){
            Picasso.get().load(item_array.get(i).getPoster()).into(itemRowHolder.poster_iv);
        }else{
            itemRowHolder.poster_iv.setImageResource(R.mipmap.default_image);
        }


        itemRowHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieDetailesActivity.class);
                intent.putExtra("imdbID", item_array.get(i).getImdbID());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return (null != item_array ? item_array.size() : 0);
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder {


        TextView title_tv;
        AppCompatImageView poster_iv;
        CardView cardView;


        public ItemRowHolder(View view) {
            super(view);

            title_tv = view.findViewById(R.id.title_tv);
            poster_iv =  view.findViewById(R.id.poster_iv);
            cardView =  view.findViewById(R.id.cardView);

        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, String id, int selected);

    }

    public void SetOnItemClickListener(final MovieAdapter.OnItemClickListener mItemClickListener) {
        this.mListener = mItemClickListener;
    }
}
