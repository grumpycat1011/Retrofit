package com.example.json2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("posts")
    //this posts is a part of the url that I am retriving data for
    //posts is also called the relative url
    Call<List<JsonModelObject>> getposts();

    @GET("/posts/1/comments")
    Call<List<Comments>> getComments();

}
