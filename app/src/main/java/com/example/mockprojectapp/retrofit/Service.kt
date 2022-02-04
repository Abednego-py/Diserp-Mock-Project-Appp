package com.example.mockprojectapp.retrofit

import retrofit2.http.GET

interface Service {
    @GET("search/users?q=lagos")
    suspend fun listUsers(): RepoResponseX
}