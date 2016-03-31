package com.example.mouadr.fs;

/**
 * Created by mouadr on 19/03/2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mouadr.fs.adater.ActualiteAdapter;
import com.example.mouadr.fs.app.AppController;
import com.example.mouadr.fs.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FtourFragment extends Fragment {


   private TopFragment    m_topFragment;
    private BottomFragment m_bottomFragment;

    private ImageButton img;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private static final String url = "http://androidinpt.olympe.in/volley/imgvolley.json";
    private ProgressDialog pDialog;
    private static final String TAG = FtourFragment.class.getSimpleName();
    private List<Movie> movieList = new ArrayList<Movie>();
    private RecyclerView.LayoutManager mLayoutManager;
    public FtourFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ftour, container, false);

        mRecyclerView = (RecyclerView)rootView. findViewById(R.id.my_recycler_view);
        img=(ImageButton)rootView.findViewById(R.id.imgshare);
        mRecyclerView.setHasFixedSize(true);

      m_topFragment = (TopFragment)getChildFragmentManager().findFragmentById(R.id.fragment_top);
     m_bottomFragment = (BottomFragment) getChildFragmentManager().findFragmentById(R.id.fragment_bottom);
        getChildFragmentManager().beginTransaction().hide( m_topFragment ).hide( m_bottomFragment ).commit();


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ActualiteAdapter(getContext(),movieList);
        mRecyclerView.setAdapter(mAdapter);
        pDialog = new ProgressDialog(getContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating(obj.getString("rating"));

                                movie.setYear(obj.getInt("releaseYear"));

                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);

                                // adding movie to movies array
                                movieList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);
       // fragmentTransaction.hide(up).hide(botom).commit();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m_topFragment.isHidden())
                {
                  //  img.setSelected(true);
                    getChildFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.abc_slide_in_top, R.anim.abc_slide_out_top
                    ).show( m_topFragment ).setCustomAnimations(
                            R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom
                    ).show(m_bottomFragment).commit();
                Toast.makeText(getContext(),"Mouad",Toast.LENGTH_LONG).show();
                }
                else
                {
                 //   img.setSelected( false );
                    getChildFragmentManager().beginTransaction().setCustomAnimations(
                            R.anim.abc_slide_in_top, R.anim.abc_slide_out_top
                    ).hide( m_topFragment ).setCustomAnimations(
                            R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom
                    ).hide( m_bottomFragment ).commit();
                }
            }
        });

        return rootView;
    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }
}