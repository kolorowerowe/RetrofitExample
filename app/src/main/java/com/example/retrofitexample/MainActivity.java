package com.example.retrofitexample;

import static com.example.retrofitexample.Utils.formatPosts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.retrofitexample.model.Post;
import com.example.retrofitexample.model.PostResponse;
import com.example.retrofitexample.model.PostsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button buttonNextPage;
    Button buttonPrevPage;
    TextView pageText;

    TextView postsText;

    EditText titleTextField;
    EditText bodyTextField;
    Button submitPostButton;

    ApiInterface apiInterface;

    int currentPage = 1;
    int maxPage = 1;


    private static final int userId = 2194;
    private static final String accessToken = "012ed1dfa9e1248e83657b41bd4b309f9a747c3107adba6f1d1a3d8bcfd8598a";

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

        titleTextField = findViewById(R.id.newPostTitle);
        bodyTextField = findViewById(R.id.newPostBody);

        submitPostButton = findViewById(R.id.newPostSubmit);
        submitPostButton.setOnClickListener(v -> createPost());

        getPosts();
    }

    private void setData(Response<PostsResponse> response) {
        Log.d("TAG", response.code() + "");

        PostsResponse postsResponse = response.body();

        pageText.setText(String.format("Current Page: \n%d", postsResponse.getMeta().getPagination().getPage()));
        postsText.setText(String.format("Posts on page: %d\n\n%s", postsResponse.getData().size(), formatPosts(postsResponse.getData())));
        maxPage = postsResponse.getMeta().getPagination().getPages();

        if (currentPage > 1) {
            buttonPrevPage.setEnabled(true);
        } else {
            buttonPrevPage.setEnabled(false);
        }

        if (currentPage < maxPage) {
            buttonNextPage.setEnabled(true);
        } else {
            buttonNextPage.setEnabled(false);
        }
    }

    private void nextPage() {
        this.currentPage += 1;
        getPosts();
    }

    private void prevPage() {
        this.currentPage -= 1;
        getPosts();
    }


    private void getPosts() {
        Call<PostsResponse> getPosts = apiInterface.doGetPosts(String.valueOf(currentPage), userId);
        getPosts.enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                setData(response);
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void createPost() {
        String title = titleTextField.getText().toString();
        String body = bodyTextField.getText().toString();
        Post newPost = new Post(0, userId, title, body);


        //TODO: this as a first exercise?
        Call<PostResponse> createPost = apiInterface.createPost(accessToken, newPost);
        createPost.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                getPosts();
                clearForm();
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void clearForm() {
        titleTextField.setText("");
        bodyTextField.setText("");
    }
}