package com.example.retrofitexample;

import com.example.retrofitexample.model.Post;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    private Utils() {
        // do nothing
    }

    public static String formatPosts(List<Post> posts) {
        return IntStream
                .range(0, posts.size())
                .mapToObj(i -> i + 1 + ". Title: " + posts.get(i).getTitle() + ".\n" + posts.get(i).getBody())
                .collect(Collectors.joining("\n\n"));
    }
}
