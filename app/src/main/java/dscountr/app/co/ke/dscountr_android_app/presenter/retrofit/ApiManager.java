package dscountr.app.co.ke.dscountr_android_app.presenter.retrofit;

import dscountr.app.co.ke.dscountr_android_app.model.User;
import dscountr.app.co.ke.dscountr_android_app.presenter.configs.Configs;
import dscountr.app.co.ke.dscountr_android_app.presenter.retrofit.interfaces.IUserApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static IUserApi service;
    private static ApiManager apiManager;

    private ApiManager() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(IUserApi.class);
    }

    public static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public void registerUser(User user, Callback<User> callback) {
        Call<User> userCall = service.registerUser(user);
        userCall.enqueue(callback);
    }
}
