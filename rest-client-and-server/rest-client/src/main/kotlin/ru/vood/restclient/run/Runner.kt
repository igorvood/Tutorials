package ru.vood.restclient.run

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import ru.vood.restclient.request.RestRequestImpl

@Component
class Runner(val requestImpl: RestRequestImpl) : CommandLineRunner {


    override fun run(vararg args: String) {
        val example = requestImpl.getObjectExample()
        Assert.notNull(example, "Must be not null")

        val arrayExample = requestImpl.getArrayExample()
        Assert.notEmpty(arrayExample, "Must be not empty")


        val postObjectExample = requestImpl.postObjectExample()
        Assert.notEmpty(postObjectExample, "Must be not empty")
    }
}