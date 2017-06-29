package com.example.testrxjava;

import com.example.testrxjava.entity.LoginResult;
import com.example.testrxjava.entity.User;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by yubo on 2017/6/29.
 */

public interface TestService {
//    @GET("user/{username}")
//    Call<ResponseBody> getUsername(@Path("username") String username);
//
//    @POST("form")
//    @FormUrlEncoded
//    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password);
//
//    @POST("headers")
//    @Headers({"key: 123456"})
//    Call<ResponseBody> testHeaders();

    @GET("user/{username}")
    Observable<User> getUser(@Path("username") String username);

    @POST("form")
    @FormUrlEncoded
    Observable<LoginResult> login(@Field("username") String username, @Field("password") String password);

    @POST("header")
    @Headers("key: helloworld")
    Observable<LoginResult> testHeaders();
}
