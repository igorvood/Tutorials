package ru.vood.user.data

class UserRepository {

    var users: Set<UserDto>

    constructor() {
        var userDto1 = UserDto("Vood", 37, HashSet())
        var userDto2 = UserDto("Tora", 36, setOf(userDto1))
        users = setOf(userDto1, userDto2)
    }

    fun getAll() = users

    fun getByName(name: String) = users.filter { u -> u.name == name }

    fun getOneAgeByName(name: String) = users
            .filter { u -> u.name == name }
            .map { it.age }
            .getOrElse(0) { 1 }

    fun getOneByName(name: String) = users
            .filter { u -> u.name == name }
            .getOrNull(0)
}