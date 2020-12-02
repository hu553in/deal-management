package com.github.hu553in.dealmanagement.exceptions

class ServiceException : Exception {
    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(message: String) : super(message)
}
