package services;

import java.util.List;

import model.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostService {
    @GET("api/posts")
    Call<List<Post>> getAll (

    );

    @GET("api/posts/{postId}")
    Call<Post> getOne(
            @Path("postId") int postId
     );

    @POST("api/posts")
    @Headers("Content-type: application/json")
    Call<Post> createPost(
            @Body Post post
    );
}
