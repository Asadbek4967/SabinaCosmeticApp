package com.example.sabinacosmeticapplication.data.remote.interceptor

import com.example.sabinacosmeticapplication.core.security.TokenManager
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestPath = originalRequest.url.encodedPath
        val token = tokenManager.getAccessToken()

        val shouldAttachToken = !token.isNullOrBlank() &&
                !requestPath.contains("/auth/login")

        val updatedRequest = if (shouldAttachToken) {
            originalRequest.newBuilder()
                .removeHeader("Authorization")
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        val response = chain.proceed(updatedRequest)

        if (response.code == 401 && shouldAttachToken) {
            tokenManager.clearSessionByUnauthorized()
        }

        return response
    }
}