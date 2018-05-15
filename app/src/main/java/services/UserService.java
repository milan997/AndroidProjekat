package services;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class used for all services, e.g. calling spring rest controllers
 */
public class UserService {

    static UserServiceInterface userServiceInterface = RetrofitObject.retrofit.create(UserServiceInterface.class);

    // TODO KAKO OVO JEBEMU SVE KRVAVVO
    public static List<User> getAll() {
        final List<User> retval = new ArrayList<>();
        Call<List<User>> call = userServiceInterface.getAll();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> users, Response<List<User>> response) {
                try {
                    // TODO resi pa ono
                    retval.add(response.body().get(0));
                    retval.add(response.body().get(1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<User>> users, Throwable t) {
                //Toast.makeText(getApplicationContext(), "ERROR FETCHING DATA!!!", Toast.LENGTH_LONG).show();
            }
        });

        return retval;
    }

}