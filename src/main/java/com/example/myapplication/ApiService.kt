package com.example.myapplication

import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field

interface ApiService {
    @FormUrlEncoded
    @POST("/predict")
    fun predict(
        @Field("profile_pic") profilePic: Int,
        @Field("username") username: String,
        @Field("fullname") fullname: String,
        @Field("description") description: String,
        @Field("external_url") externalUrl: Int,
        @Field("private") isPrivate: Int,
        @Field("number_of_posts") numberOfPosts: Int,
        @Field("number_of_followers") numberOfFollowers: Int,
        @Field("number_of_following") numberOfFollowing: Int
    ): Call<PredictionResponse>
}
