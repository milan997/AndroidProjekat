package services;

import java.util.List;

import model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("api/users")
    Call<List<User>> getAll (
            // implementirat TODO
    );

    @GET("api/users/{userId}")
    Call<User> getOne (
            @Path("userId") int userId
    );

    /**
     * A method used for logging in user. It searches for username and password matching, returns user if it exists, null if not
     * @return User
     */
    @FormUrlEncoded
    @POST("api/users/login")
    Call<User> checkLogin(
            @Field("username") String username,
            @Field("password") String password
    );
}
