package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.retrofitexample.model.Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView activityText;
    TextView accessibilityText;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityText = (TextView) findViewById(R.id.activity);
        accessibilityText = (TextView) findViewById(R.id.accessibility);

        apiInterface = APIClient.getClient().create(ApiInterface.class);


        Call<Activity> call = apiInterface.doGetActivity();
        call.enqueue(new Callback<Activity>() {
            @Override
            public void onResponse(Call<Activity> call, Response<Activity> response) {

                Log.d("TAG", response.code() + "");

                Activity activity = response.body();

                activityText.setText("Activity: " + activity.getActivity());
                accessibilityText.setText("Accessibility: " + activity.getAccessibility().toString());

            }

            @Override
            public void onFailure(Call<Activity> call, Throwable t) {
                call.cancel();
            }
        });
    }
}