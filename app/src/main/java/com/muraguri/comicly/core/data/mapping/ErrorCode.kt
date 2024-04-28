package com.muraguri.comicly.core.data.mapping

sealed class CustomApiException(message: String) : Exception(message)
class InvalidApiKeyException : CustomApiException("Invalid API Key")
class ObjectNotFoundException : CustomApiException("Object Not Found")
class UrlFormatException : CustomApiException("Error in URL Format")
class JsonpFormatException : CustomApiException("'jsonp' format requires a 'json_callback' argument")
class FilterErrorException : CustomApiException("Filter Error")
class SubscriberOnlyException : CustomApiException("Subscriber only video is for subscribers only")
class UnknownErrorCode : CustomApiException("Unknown error")

fun mapErrorCodeToException(statusCode: Int): CustomApiException {
    return when (statusCode) {
        100 -> InvalidApiKeyException()
        101 -> ObjectNotFoundException()
        102 -> UrlFormatException()
        103 -> JsonpFormatException()
        104 -> FilterErrorException()
        105 -> SubscriberOnlyException()
        else -> UnknownErrorCode()
    }
}
