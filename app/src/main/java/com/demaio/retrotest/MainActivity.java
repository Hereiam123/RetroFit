package com.demaio.retrotest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com").addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //getPosts();
        //getComments();
        createPost();
    }

    private void getPosts(){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                List<Post> posts = response.body();

                for(Post post: posts){
                    String content = "";
                    content += "ID: "+post.getId()+ "\n";
                    content += "User ID: "+post.getUserId()+"\n";
                    content+= "Title: "+post.getTitle()+"\n";
                    content+="Text: "+post.getText()+"\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                List<Comment> comments = response.body();

                for(Comment comment: comments){
                    String content = "";
                    content += "ID: "+comment.getId()+ "\n";
                    content+="Text: "+comment.getText()+"\n\n";
                    content += "ID: "+comment.getPostId()+ "\n";
                    content+="Text: "+comment.getEmail()+"\n\n";
                    content+="Text: "+comment.getName()+"\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost(){
        final Post post = new Post(23, "New Title", "New Text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "New Title");
        // Call<Post> call = jsonPlaceHolderApi.createPost(post);
        //Call<Post> call = jsonPlaceHolderApi.createPost(23, "New Title", "New Text");
        Call<Post> call = jsonPlaceHolderApi.createPost(fields);


        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content +="Code: "+response.code()+"\n";
                content += "ID: "+postResponse.getId()+ "\n";
                content+="Text: "+postResponse.getText()+"\n\n";
                content += "ID: "+postResponse.getUserId()+ "\n";
                content+="Text: "+postResponse.getTitle()+"\n\n";
                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
