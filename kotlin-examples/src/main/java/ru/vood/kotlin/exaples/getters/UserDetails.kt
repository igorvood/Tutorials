package ru.vood.kotlin.exaples.getters

import java.io.Serializable

interface UserDetails : Serializable {
    fun getPassword(): String
    fun getUsername(): String

}