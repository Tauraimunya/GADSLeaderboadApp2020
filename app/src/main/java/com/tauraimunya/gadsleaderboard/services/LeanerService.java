package com.tauraimunya.gadsleaderboard.services;

import com.tauraimunya.gadsleaderboard.model.Learner;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LeanerService {
    @GET("hours")
    Call<List<Learner>> learnerHours();

    @GET("skilliq")
    Call<List<Learner>> learnerSkillIQ();

    @FormUrlEncoded
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    Call<ResponseBody> submitProject(
            @Field("entry.1877115667") String firstName,
            @Field("entry.2006916086") String lastName,
            @Field("entry.1824927963") String businessEmail,
            @Field("entry.284483984") String projectLink

    );
}
