package ru.vood.responce.exeption

interface HandleExceptionService {

    fun handle(id: String, exception: Throwable, errorText: String)
}