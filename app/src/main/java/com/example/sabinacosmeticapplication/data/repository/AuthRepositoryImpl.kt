package com.example.sabinacosmeticapplication.data.repository

import com.example.sabinacosmeticapplication.core.security.TokenManager
import com.example.sabinacosmeticapplication.data.remote.api.AuthApiService
import com.example.sabinacosmeticapplication.data.remote.dto.auth.LoginRequestDto
import com.example.sabinacosmeticapplication.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return runCatching {
            val response = authApiService.login(
                LoginRequestDto(
                    email = email.trim(),
                    password = password
                )
            )

            if (!response.success) {
                throw IllegalStateException(response.message ?: "Login failed")
            }

            val accessToken = response.data?.accessToken?.trim().orEmpty()

            if (accessToken.isBlank()) {
                throw IllegalStateException("Access token topilmadi")
            }

            tokenManager.saveAccessToken(accessToken)
        }
    }

    override suspend fun logout() {
        tokenManager.clearSession()
    }
}