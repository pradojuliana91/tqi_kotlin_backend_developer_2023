package br.com.jumarket.autoatendimentoapi.controller

import br.com.jumarket.autoatendimentoapi.exception.BadRequestException
import br.com.jumarket.autoatendimentoapi.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleIllegalStateException(ex: Exception): ResponseEntity<List<ErrorMessage>> {

        val errorMessage = ErrorMessage( code = "INTERNAL_SERVER_ERROR", message = ex.message ?: "")

        return ResponseEntity(listOf(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler
    fun handleIllegalStateException(ex: NotFoundException): ResponseEntity<List<ErrorMessage>> {
        val errorMessage = ErrorMessage( code = "NOT_FOUND", message = ex.message ?: "")

        return ResponseEntity(listOf(errorMessage), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleIllegalStateException(ex: BadRequestException): ResponseEntity<List<ErrorMessage>> {
        val errorMessage = ErrorMessage( code = "BAD_REQUEST", message = ex.message ?: "")

        return ResponseEntity(listOf(errorMessage), HttpStatus.BAD_REQUEST)
    }
}

data class ErrorMessage(
    var code: String,
    var message: String
)