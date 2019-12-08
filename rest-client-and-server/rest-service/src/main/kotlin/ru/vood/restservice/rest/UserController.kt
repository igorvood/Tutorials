package ru.vood.restservice.rest

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import ru.vood.user.data.UserDto
import ru.vood.user.data.UserRepository
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@RestController
class UserController {
    private val LOGGER = Logger.getLogger(UserController::class.java.name)

    private val template = "Hello, %s!"
    private val counter = AtomicLong()

    private val userRepository = UserRepository()

    @RequestMapping("/users/all")
    fun gatAll() = userRepository.getAll()

    @GetMapping("/users/byName")
    fun getByName(name: String): List<UserDto> {
        LOGGER.info("name -> $name,  size -> ${userRepository.getByName(name).size}")
        return userRepository.getByName(name)
    }

    @GetMapping("/users/oneByName")
    fun getOneByName(@RequestParam name: String): UserDto? {
        LOGGER.info("name -> $name,  dto -> ${userRepository.getOneByName(name)}")
        val oneByName = userRepository.getOneByName(name)
        return oneByName
    }

    @PostMapping(path = ["/users/byName"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getIfExists(@RequestBody user: UserDto): List<UserDto> {
//        LOGGER.info("name -> $(name.,  size -> ${userRepository.getByName(name).size}")

        return userRepository.getAll()
                .filter { it.name == user.name && it.age == user.age }
    }


}