package com.github.hu553in.dealmanagement.components.validators

abstract class Validator<T> {
    fun validate(value: T): Map<String, String> {
        val errors: MutableMap<String, String> = mutableMapOf()
        validateInternal(errors, value)
        return errors
    }

    protected abstract fun validateInternal(errors: MutableMap<String, String>, value: T)
}
