package com.example.sabinacosmeticapplication.data.remote.dto.auth

data class LoginResponseDto(
    val success: Boolean,
    val message: String?,
    val data: LoginDataDto?
)

data class LoginDataDto(
    val user: AuthUserDto?,
    val accessToken: String?
)

data class AuthUserDto(
    val id: String?,
    val fullName: String?,
    val email: String?,
    val role: String?,
    val isActive: Boolean?,
    val createdAt: String?,
    val updatedAt: String?
)