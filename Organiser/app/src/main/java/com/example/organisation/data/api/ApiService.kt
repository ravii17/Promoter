package com.example.organisation.data.api

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

data class SendOtpRequest(val recipient: String)
data class SendOtpResponse(val success: Boolean, val message: String)

data class VerifyOtpRequest(val recipient: String, val otp: String)
data class VerifyOtpResponse(
    val token: String,
    val is_new_user: Boolean,
    val user_id: String,
    val phone: String?,
    val email: String?,
    val name: String,
    val city: String,
    val role: String,
    val kyc_status: String,
    val profile_photo_url: String,
    val profile_complete: Boolean
)

data class UpdateRoleRequest(val role: String)
data class UpdateRoleResponse(
    val success: Boolean,
    val user_id: String,
    val role: String,
    val name: String,
    val city: String,
    val kyc_status: String,
    val profile_photo_url: String
)

data class ProfileRequest(
    val name: String,
    val email: String,
    val city: String,
    val profile_photo_url: String = ""
)

data class ProfileResponse(
    val success: Boolean,
    val user_id: String,
    val phone: String?,
    val email: String?,
    val name: String,
    val city: String,
    val role: String,
    val kyc_status: String,
    val profile_photo_url: String
)

data class PhotoUploadResponse(
    val success: Boolean,
    val profile_photo_url: String
)

// Legacy phone-only models for backward compatibility
data class LegacySendOtpRequest(val phone: String)
data class LegacyVerifyOtpRequest(val phone: String, val otp: String)

interface ApiService {
    @POST("auth/otp")
    suspend fun sendOtp(@Body request: SendOtpRequest): SendOtpResponse

    @POST("auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): VerifyOtpResponse

    @PATCH("users/role")
    suspend fun updateRole(@Body request: UpdateRoleRequest): UpdateRoleResponse

    @POST("users/profile")
    suspend fun saveProfile(@Body request: ProfileRequest): ProfileResponse

    @Multipart
    @POST("users/profile/photo")
    suspend fun uploadPhoto(@Part photo: MultipartBody.Part): PhotoUploadResponse
}
