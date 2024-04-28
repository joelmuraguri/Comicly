package com.muraguri.comicly.core.remote.utils

import retrofit2.Response

fun <T> handleStatusCode(response: Response<T>): Result<T> {
    if (response.isSuccessful) {
        val body = response.body()

        if (body is Map<*, *>) {
            val statusCode = body["status_code"] as? Int

            return if (statusCode == 1) {  // 1 is the acceptable status code
                Result.success(body)
            } else {
                val errorMessage = when (statusCode) {
                    100 -> "Invalid API Key"
                    101 -> "Object Not Found"
                    102 -> "Error in URL Format"
                    103 -> "'jsonp' format requires a 'json_callback' argument"
                    104 -> "Filter Error"
                    105 -> "Subscriber-only video is for subscribers only"
                    else -> "Unknown error"
                }
                Result.failure(Exception("Error: $errorMessage"))  // Return error message
            }
        } else {
            // If body does not have expected structure
            return Result.failure(Exception("Unexpected response structure"))
        }
    } else {
        // Handle unsuccessful responses from the server
        val errorBody = response.errorBody()?.string() ?: "Unknown error"
        return Result.failure(Exception("Error: $errorBody"))
    }
}
