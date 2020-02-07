package com.sharath.moviesearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.gson.JsonElement;
import com.sharath.moviesearch.Utils.APIService;
import com.sharath.moviesearch.Utils.ApiUtils;
import com.sharath.moviesearch.Utils.ProgressDialogClass;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailesActivity extends AppCompatActivity {

    Activity activity;
    APIService mAPIService;
    String imdbID;
    @BindView(R.id.poster_iv)
    AppCompatImageView poster_iv;
    @BindView(R.id.story_line_tv)
    TextView story_line_tv;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.back_lay)
    LinearLayout back_lay;
    ProgressDialogClass progressDialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detailes);

        ButterKnife.bind(this);
        activity = MovieDetailesActivity.this;
        progressDialogClass = new ProgressDialogClass(this);

        imdbID = getIntent().getStringExtra("imdbID");
        System.out.println("imdbID+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + imdbID);
        mAPIService = ApiUtils.apiService();

        back_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        movie_link();
    }

    private void movie_link() {

        progressDialogClass.callProgress();
        final Map<String, String> a = new HashMap<>();
        a.put("i", imdbID);
        final Map<String, String> b = new HashMap<>();
        a.put("apikey", "f220394c");

        mAPIService.movie(a, b).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {

                System.out.println("Response in movie list--->" + response.body());
                if (response.code() == 200) {
                    String dd = response.body().toString();
                    try {
                        JSONObject object = new JSONObject(dd);

                        String Title = object.getString("Title");
                        String Poster = object.getString("Poster");
                        String Plot = object.getString("Plot");

                        story_line_tv.setText(Plot);
                        title_tv.setText(Title);
                        if (!Poster.equals("")) {
                            Picasso.get().load(Poster).into(poster_iv);
                        } else {
                            poster_iv.setImageResource(R.mipmap.default_image);
                        }

                    } catch (Exception e) {

                    }progressDialogClass.hideDialog();

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
