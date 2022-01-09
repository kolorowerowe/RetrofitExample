package com.example.retrofitexample;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    private Utils() {
        // do nothing
    }

    public static String formatPosts(List<String> posts) {
        return IntStream
                .range(0, posts.size())
                .mapToObj(i -> i + 1 + ". " + posts.get(i))
                .collect(Collectors.joining("\n\n"));
    }
}
