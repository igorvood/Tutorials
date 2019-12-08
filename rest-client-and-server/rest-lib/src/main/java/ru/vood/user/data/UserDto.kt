package ru.vood.user.data

data class UserDto(val name: String
                   , val age: Int
                   , val relation: Set<UserDto>
)