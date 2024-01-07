package com.wa2c.android.cifsdocumentsprovider.domain.exception

/**
 * Edit exception.
 */
sealed class EditException : RuntimeException() {
    class InputRequiredException : EditException()
    class InvalidIdException : EditException()
    class DuplicatedIdException : EditException()
}
