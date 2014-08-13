package com.luizgadao.volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.luizgadao.volley.adapter.CustomListAdapter;
import com.luizgadao.volley.app.AppController;
import com.luizgadao.volley.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SimpleListView extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1b1b1b")));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        // Log tag
        private static final String TAG = SimpleListView.class.getSimpleName();

        // Movies json url
        private static final String url = "http://api.androidhive.info/json/movies.json";
        private ProgressDialog pDialog;
        private List<Movie> movieList = new ArrayList<Movie>();
        private ListView listView;
        private CustomListAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            listView = (ListView) rootView.findViewById(R.id.list);
            adapter = new CustomListAdapter(getActivity(), movieList);
            listView.setAdapter(adapter);

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

            JsonArrayRequest movieReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, response.toString());
                    hideDialog();

                    for (int i = 0; i < response.length(); i++)
                    {
                        try {
                            JSONObject object = response.getJSONObject(i);
                            Movie movie = new Movie();
                            movie.setTitle( object.getString("title") );
                            movie.setThumbnailUrl( object.getString("image") );
                            movie.setRating( object.getDouble("rating") );
                            movie.setYear( object.getInt("releaseYear") );

                            JSONArray genreArray = object.getJSONArray("genre");
                            ArrayList<String> genre = new ArrayList<String>();

                            for (int j = 0; j < genreArray.length(); j++)
                            {
                                genre.add( genreArray.get(j).toString() );
                            }

                            movie.setGenre(genre);
                            movieList.add(movie);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                    hideDialog();
                }
            });

            AppController.getInstance().addToRequestQueue(movieReq);

            return rootView;
        }

        private void hideDialog() {
            if (pDialog != null)
            {
                pDialog.dismiss();
                pDialog = null;
            }
        }
    }
}
