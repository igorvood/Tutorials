package ru.vood.restclient.request

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.vood.user.data.UserDto
import java.util.logging.Logger


@Service
class RestRequestImpl(val restTemplate: RestTemplate) {


//  еще ПРИМЕРЫ ТУТ
//    https://o7planning.org/ru/11647/spring-boot-restful-client-with-resttemplate-example#a13907929

    private val LOGGER = Logger.getLogger(RestRequestImpl::class.java.name)
    val HOST = "http://localhost:8088"

    fun getObjectExample(): UserDto {
        var forObject: UserDto
        val build = UriComponentsBuilder.fromHttpUrl("$HOST/users/oneByName")
                .queryParam("name", "Vood")
                .build()
        forObject = restTemplate.getForObject(build.toUriString(), UserDto::class.java)!!
        LOGGER.info("getObjectExample  ${forObject.age}")
        return forObject
    }

    fun getArrayExample(): Array<UserDto> {
        val build = UriComponentsBuilder.fromHttpUrl("$HOST/users/all")
                .queryParam("name", "Vood")
                .build()
        val forObject = restTemplate.getForObject(build.toUriString(), Array<UserDto>::class.java)!!
        LOGGER.info("getArrayExample  ${forObject.size}")
        return forObject
    }

    fun postObjectExample(): Array<UserDto> {

        val userDto = UserDto("Vood", 37, emptySet())

        val headers = HttpHeaders()
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.contentType = MediaType.APPLICATION_JSON

        val requestBody: HttpEntity<UserDto> = HttpEntity<UserDto>(userDto, headers)

        val postForObject = restTemplate.postForObject("$HOST/users/byName", requestBody, Array<UserDto>::class.java)!!
        LOGGER.info("postObjectExample  ${postForObject.size}")
        return postForObject

    }

}