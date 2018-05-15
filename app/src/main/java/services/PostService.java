package services;

import java.util.List;

import model.Post;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {
    @GET("api/users")
    Call<List<Post>> getPosts (
            // implementirat TODO
    );
}
