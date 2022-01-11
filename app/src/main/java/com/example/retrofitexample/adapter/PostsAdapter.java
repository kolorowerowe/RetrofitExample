package com.example.retrofitexample.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.retrofitexample.ApiClient;
import com.example.retrofitexample.ApiInterface;
import com.example.retrofitexample.MainActivity;
import com.example.retrofitexample.R;
import com.example.retrofitexample.model.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PostsAdapter extends ArrayAdapter<Post> {
    private final List<Post> postList = new ArrayList<>();
    ApiInterface apiInterface;


    public PostsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

    }

    @Override
    public void add(Post object) {
        postList.add(object);
        super.add(object);
    }

    public void addAllWithClear(@NonNull Collection<? extends Post> collection) {
        this.clear();
        postList.addAll(collection.stream().sorted(Comparator.comparingInt(Post::getId)).collect(Collectors.toList()));
    }

    @Override
    public void clear() {
        postList.clear();
        super.clear();
    }

    @Override
    public int getCount() {
        return this.postList.size();
    }

    @Override
    public Post getItem(int index) {
        return this.postList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PostViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.post_view, parent, false);
            viewHolder = new PostViewHolder();
            viewHolder.title = row.findViewById(R.id.post_element_title);
            viewHolder.body = row.findViewById(R.id.post_element_body);
            viewHolder.deleteButton= row.findViewById(R.id.delete_post_button);




            row.setTag(viewHolder);
        } else {
            viewHolder = (PostViewHolder) row.getTag();
        }
        Post post = getItem(position);
        viewHolder.title.setText(post.getTitle());
        viewHolder.body.setText(post.getBody());
        viewHolder.deleteButton.setOnClickListener(v -> deletePost(post.getId()));


        return row;
    }

    static class PostViewHolder {
        TextView title;
        TextView body;
        Button deleteButton;
    }

    private void deletePost(int postId){
        if (getContext() instanceof MainActivity) {
            ((MainActivity)getContext()).deletePost(postId);
        }
    }


}
