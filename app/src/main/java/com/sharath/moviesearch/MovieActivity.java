package com.sharath.moviesearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonElement;
import com.sharath.moviesearch.Utils.APIService;
import com.sharath.moviesearch.Utils.ApiUtils;
import com.sharath.moviesearch.Utils.ProgressDialogClass;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {

    Activity activity;
    APIService mAPIService;
    String title;
    private ArrayList<MovieModel> movie_list = new ArrayList<>();
    private MovieAdapter mAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.empty_lay)
    LinearLayout empty_lay;
    ProgressDialogClass progressDialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);
        activity = MovieActivity.this;
        progressDialogClass = new ProgressDialogClass(this);

        title = getIntent().getStringExtra("search_title");
        System.out.println("title+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + title);
        mAPIService = ApiUtils.apiService();

        movie_link();
    }

    private void movie_link() {

        progressDialogClass.callProgress();
        final Map<String, String> a = new HashMap<>();
        a.put("s", title);
        final Map<String, String> b = new HashMap<>();
        a.put("apikey", "f220394c");

        mAPIService.movielist(a, b).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {

                System.out.println("Response in movie list--->" + response.body());
                if (response.code() == 200) {
                    String dd = response.body().toString();
                    try {
                        JSONObject object = new JSONObject(dd);
                        JSONArray array = object.getJSONArray("Search");

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject as = array.getJSONObject(i);
                            String Title = as.getString("Title");
                            String Poster = as.getString("Poster");
                            String imdbID = as.getString("imdbID");

                            MovieModel movieModel = new MovieModel(Title,"",imdbID,"", Poster);
                            movie_list.add(movieModel);

                        }

                        mAdapter = new MovieAdapter(activity, movie_list);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(activity, 2);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);

                    } catch (Exception e) {

                    }progressDialogClass.hideDialog();

                    if(movie_list.size()==0){
                        recyclerView.setVisibility(View.GONE);
                        empty_lay.setVisibility(View.VISIBLE);
                    }else {
                        recyclerView.setVisibility(View.VISIBLE);
                        empty_lay.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                System.out.println("response" + "Error------------->response of movie list");
                System.out.println(t.getMessage());
                progressDialogClass.hideDialog();

            }
        });
    }
}
