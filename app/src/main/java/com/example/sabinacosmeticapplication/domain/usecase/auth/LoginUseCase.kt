package com.example.sabinacosmeticapplication.domain.usecase.auth

import com.example.sabinacosmeticapplication.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> {
        return authRepository.login(email, password)
    }
}