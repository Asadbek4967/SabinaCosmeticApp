package com.example.sabinacosmeticapplication.data.remote.api

import com.example.sabinacosmeticapplication.data.remote.dto.auth.LoginRequestDto
import com.example.sabinacosmeticapplication.data.remote.dto.auth.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequestDto
    ): LoginResponseDto
}