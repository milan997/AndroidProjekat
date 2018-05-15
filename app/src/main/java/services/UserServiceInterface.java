package services;

import java.util.List;

import model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserServiceInterface {
    @GET("api/users")
    Call<List<User>> getAll (
            // implementirat TODO
    );

    @GET("api/users/{userId}")
    Call<User> getOne (
            @Path("userId") int userId
    );

}
