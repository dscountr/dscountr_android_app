package dscountr.app.co.ke.dscountr_android_app.presenter.retrofit.interfaces;

import dscountr.app.co.ke.dscountr_android_app.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IUserApi {

    @Headers("Content-Type: application/json")
    @POST("users/")
    Call<User> registerUser(@Body User user);

    @Headers("Content-Type: application/json")
    @GET("login")
    Call<User> loginUser(@Query("phone_number") String phone_number);
}
