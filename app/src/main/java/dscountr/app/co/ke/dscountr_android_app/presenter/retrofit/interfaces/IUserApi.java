package dscountr.app.co.ke.dscountr_android_app.presenter.retrofit.interfaces;

import dscountr.app.co.ke.dscountr_android_app.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IUserApi {

    @Headers("Content-Type: application/json")
    @POST("users/")
    Call<User> registerUser(@Body User user);
}
