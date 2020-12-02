package com.github.hu553in.dealmanagement.exceptions

class RepositoryException : Exception {
    constructor(message: String, cause: Throwable) : super(message, cause)

    constructor(message: String) : super(message)
}
