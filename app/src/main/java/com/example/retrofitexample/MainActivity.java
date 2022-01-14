package com.example.retrofitexample;

import static com.example.retrofitexample.Utils.formatPosts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitexample.adapter.PostsAdapter;
import com.example.retrofitexample.model.Post;
import com.example.retrofitexample.model.PostResponse;
import com.example.retrofitexample.model.PostsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button buttonNextPage;
    Button buttonPrevPage;
    TextView pageText;

    ListView postsListView;
    PostsAdapter postsAdapter;

    EditText titleTextField;
    EditText bodyTextField;
    Button submitPostButton;

    ApiInterface apiInterface;

    int currentPage = 1;
    int maxPage = 1;


    private static final int userId = 1597;
    private static final String accessToken = "012ed1dfa9e1248e83657b41bd4b309f9a747c3107adba6f1d1a3d8bcfd8598a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pageText = (TextView) findViewById(R.id.page_text);

        getLayoutInflater().inflate(R.layout.post_view, postsListView);

        postsAdapter = new PostsAdapter(this, R.layout.post_view);
        postsListView = findViewById(R.id.postListView);
        postsListView.setAdapter(postsAdapter);


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

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    private void setData(Response<PostsResponse> response) {
        Log.d("TAG", response.code() + "");

        PostsResponse postsResponse = response.body();

        pageText.setText(String.format("Current Page: \n%d", postsResponse.getMeta().getPagination().getPage()));

        postsAdapter.addAllWithClear(response.body().getData());

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
                if (response.isSuccessful()) {
                    setData(response);
                } else if (response.code() == 404) {
                    Toast.makeText(getApplicationContext(), "404 Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void createPost() {
        String title = titleTextField.getText().toString();
        String body = bodyTextField.getText().toString();
        Post newPost = new Post(1, userId, title, body);


        //TODO - First exercise -  Handle 422 response code, returned when empty message is sent
        Call<PostResponse> createPost = apiInterface.createPost(accessToken, newPost);
        createPost.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    getPosts();
                    clearForm();
                } else {
                    Toast.makeText(getApplicationContext(), "Error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void clearForm() {
        titleTextField.setText("");
        bodyTextField.setText("");
    }

    public void deletePost(int postId) {
        Call<Void> createPost = apiInterface.deletePost(postId, accessToken);
        createPost.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getPosts();
                } else {
                    Toast.makeText(getApplicationContext(), "Error " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });

    }
}