package com.example.mouadr.fs.adater;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mouadr.fs.R;
import com.example.mouadr.fs.app.AppController;
import com.example.mouadr.fs.model.Movie;


import java.util.List;

/**
 * Created by raf3 on 31/03/2016.
 */
public class ActualiteAdapter extends RecyclerView.Adapter<ActualiteAdapter.ActualiteViewHolder>{

    private static final int Logo = 0;
    private static final int Data = 1;

    private ProgressDialog pDialog;

    private List<Movie> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private CustomListAdapter adapter;
    Context context;


    public ActualiteAdapter(Context context,List<Movie> list) {
        this.context = context;
        this.movieItems=list;
    }

    @Override
    public ActualiteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        ActualiteViewHolder holder = null;
        switch (viewType) {
            case Logo:
                view = LayoutInflater.from(context).inflate(R.layout.logo, parent, false);
                holder = new ActualiteViewHolder(view, Logo);
                break;
            case Data:
                view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
                holder = new ActualiteViewHolder(view, Data);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ActualiteViewHolder holder, int position) {
        int pos = getItemViewType(position);


        if(pos== Data){

            Movie m = movieItems.get(position);


            holder.thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

            // title
            holder.title.setText(m.getTitle());

            // rating
            holder.rating.setText(String.valueOf(m.getRating()));

            // genre
            String genreStr = "";
            for (String str : m.getGenre()) {
                genreStr += str + ", ";
            }
            genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                    genreStr.length() - 2) : genreStr;


            // release year
            holder.year.setText(String.valueOf(m.getYear()));


        }
    }


    @Override
    public int getItemViewType(int position) {


        if(position==0){
            return Logo;
        }else
            return Data;
    }

    @Override
    public int getItemCount() {
        return movieItems.size();
    }


    public static class ActualiteViewHolder extends RecyclerView.ViewHolder{
        TextView  title,rating,year;
        NetworkImageView thumbNail;
        public ActualiteViewHolder(View view, int viewType) {
            super(view);
            switch (viewType) {
                case Logo:

                    break;
                case Data:
                    //imageLoader = AppController.getInstance().getImageLoader();
                    thumbNail = (NetworkImageView) view
                            .findViewById(R.id.thumbnail);
                    title = (TextView) view.findViewById(R.id.title);
                    rating = (TextView) view.findViewById(R.id.rating);
                    year = (TextView) view.findViewById(R.id.releaseYear);
                    break;
            }
        }
    }

}
