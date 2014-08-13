package com.luizgadao.volley.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.luizgadao.volley.R;
import com.luizgadao.volley.app.AppController;
import com.luizgadao.volley.model.Movie;

import java.util.List;

/**
 * Created by luizcarlos on 12/08/14.
 */
public class CustomListAdapter extends BaseAdapter
{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Movie> movieItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Movie> movieItems) {
        this.activity = activity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.rating = (TextView) convertView.findViewById(R.id.rating);
            holder.genre = (TextView) convertView.findViewById(R.id.genre);
            holder.year = (TextView) convertView.findViewById(R.id.releaseYear);

            convertView.setTag( holder );
        }
        else
            holder = (ViewHolder) convertView.getTag();

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        //default image
        thumbNail.setDefaultImageResId(R.drawable.ic_no_image);
        //error load
        //thumbNail.setErrorImageResId(R.drawable.ic_error_image);


        // getting movie data
        Movie movie = movieItems.get(position);
        // thumbnail image
        thumbNail.setImageUrl( movie.getThumbnailUrl(), imageLoader );
        //title
        holder.title.setText(movie.getTitle());
        //rating
        holder.rating.setText("Rating: " + String.valueOf(movie.getRating()));

        // genre
        String genreStr = "";
        for (String str : movie.getGenre()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,genreStr.length() - 2) : genreStr;
        holder.genre.setText(genreStr);

        // release year
        holder.year.setText( String.valueOf(movie.getYear()) );

        return convertView;
    }

    static class ViewHolder
    {
        TextView title;
        TextView rating;
        TextView genre;
        TextView year;
    }
}
