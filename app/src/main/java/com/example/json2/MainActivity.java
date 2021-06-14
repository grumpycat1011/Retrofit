package com.example.json2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    Api apiobject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= findViewById(R.id.textviewxml);

        Retrofit retrofitobject = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())//this is how we define that we want to use gson to parse the response
                .build();
        apiobject = retrofitobject.create(Api.class);

        //getPosts();
        getComments();
    }
    private void getPosts(){
        Call<List<JsonModelObject>> call = apiobject.getposts();
        call.enqueue(new Callback<List<JsonModelObject>>() {
            @Override
            public void onResponse(Call<List<JsonModelObject>> call, Response<List<JsonModelObject>> response) {
                if(!response.isSuccessful()){
                    textView.setText("code: "+ response.code());
                    return;
                }
                List<JsonModelObject> posts = response.body();

                for(JsonModelObject post: posts ){
                    String content = " ";
                    content += "Id: " + post.getId() + "\n";
                    content += "UserId: " + post.getUserid() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Body: " + post.getBody() + "\n\n";

                    textView.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<JsonModelObject>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });//this meathod here helps craete a new thread to execute network operations. IF we perform network operations in main thread we get an exception that will freeze up our app. Nornmally a new thread would be needed to be formaed
    }
    private void getComments(){
        Call<List<Comments>> call = apiobject.getComments();
        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if(!response.isSuccessful()){
                    textView.setText("code: "+ response.code());
                    return;
                }
                List<Comments> listobjeccomment = response.body();
                for(Comments comments : listobjeccomment){
                    String content = " ";
                    content += "ID: " +comments.getId() + "\n";
                    content += "Post ID: " +comments.getPostId() + "\n";
                    content += "NAme: " +comments.getName() + "\n";
                    content += "Email: " +comments.getEmail() + "\n";
                    content += "Text: " +comments.getBody() + "\n\n";
                    textView.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                textView.setText(t.getMessage());
            }
        });
    }
}