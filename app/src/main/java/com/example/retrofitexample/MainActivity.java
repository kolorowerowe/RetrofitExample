package com.example.retrofitexample;

import static com.example.retrofitexample.Utils.formatPosts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.retrofitexample.model.Post;
import com.example.retrofitexample.model.PostResponse;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView pageText;
    TextView postsText;
    ApiInterface apiInterface;
    int currentPage = 1;
    int maxPage = 1;
    String userId = "2194";

    Button buttonNextPage;
    Button buttonPrevPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageText = (TextView) findViewById(R.id.page_text);
        postsText = (TextView) findViewById(R.id.postsText);
        postsText.setMovementMethod(new ScrollingMovementMethod());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        buttonNextPage = findViewById(R.id.button_next_page);
        buttonNextPage.setOnClickListener(v -> nextPage());
        buttonNextPage.setEnabled(false);

        buttonPrevPage = findViewById(R.id.button_prev_page);
        buttonPrevPage.setOnClickListener(v -> prevPage());
        buttonPrevPage.setEnabled(false);

        getPosts();
    }

    private void setData(Response<PostResponse> response) {
        Log.d("TAG", response.code() + "");

        PostResponse postResponse = response.body();

        pageText.setText(String.format("Current Page: \n%d", postResponse.getMeta().getPagination().getPage()));
        List<String> posts = postResponse.getData().stream().map(Post::getTitle).collect(Collectors.toList());
        postsText.setText(String.format("Posts on page: %d\n\n%s", postResponse.getData().size(), formatPosts(posts)));
        maxPage = postResponse.getMeta().getPagination().getPages();

        if (currentPage > 1){
            buttonPrevPage.setEnabled(true);
        } else {
            buttonPrevPage.setEnabled(false);
        }

        if (currentPage < maxPage){
            buttonNextPage.setEnabled(true);
        } else {
            buttonNextPage.setEnabled(false);
        }
    }

    private void nextPage(){
        this.currentPage += 1;
        getPosts();
    }

    private void prevPage(){
        this.currentPage -= 1;
        getPosts();
    }


    private void getPosts() {
        Call<PostResponse> getPosts = apiInterface.doGetPosts(String.valueOf(currentPage), userId);
        getPosts.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                setData(response);
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
}