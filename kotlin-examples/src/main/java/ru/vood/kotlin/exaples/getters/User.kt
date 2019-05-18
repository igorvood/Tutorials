package ru.vood.kotlin.exaples.getters


//Data генерирует getter и при имплементации UserDetails получаем не валид, поэтому надо сделать
// поля приватными и заоверрайдить методы интерфейса
data class User(
        private val username: String,
        private val password: String
) : UserDetails {
    override fun getPassword() = password

    override fun getUsername() = username
}