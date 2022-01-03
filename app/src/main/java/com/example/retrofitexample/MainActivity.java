package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.retrofitexample.model.PostResponse;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView activityText;
    TextView accessibilityText;
    ApiInterface apiInterface;
    int postPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityText = (TextView) findViewById(R.id.activity);
        accessibilityText = (TextView) findViewById(R.id.accessibility);
        accessibilityText.setMovementMethod(new ScrollingMovementMethod());
        apiInterface = APIClient.getClient().create(ApiInterface.class);

        Button button = findViewById(R.id.button_load_next_posts);
        button.setOnClickListener(v -> GetPosts());
    }

    private void SetData(Response<PostResponse> response) {
        Log.d("TAG", response.code() + "");

        PostResponse postResponse = response.body();

        activityText.setText(String.format("Current Page: %d \nNext page: %s",
                postResponse.getMeta().getPagination().getPage(),
                postResponse.getMeta().getPagination().getLinks().getNext()));
        List<String> posts = postResponse.getData().stream().map(entity -> entity.getTitle()).collect(Collectors.toList());
        accessibilityText.setText(String.format("Posts on page: %d\n %s", postResponse.getData().size(), String.join(",\n", posts)));
        postPage = postResponse.getMeta().getPagination().getPage() + 1;
    }

    private void GetPosts() {
        Call<PostResponse> getPosts = apiInterface.doGetPosts(String.valueOf(postPage));
        getPosts.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                SetData(response);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}