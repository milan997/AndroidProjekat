package services;

import java.util.List;

import model.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @DELETE("api/posts/{postId}")
    Call<ResponseBody> deletePost(
            @Path("postId") int postId
    );

    @PATCH("api/posts/{postId}/like")
    Call<ResponseBody> likePost(
            @Path("postId") int postId
    );

    @PATCH("api/posts/{postId}/dislike")
    Call<ResponseBody> dislikePost(
            @Path("postId") int postId
    );

}
