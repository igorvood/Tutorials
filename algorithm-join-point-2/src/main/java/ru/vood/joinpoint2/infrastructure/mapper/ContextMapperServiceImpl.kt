package ru.vood.joinpoint2.infrastructure.mapper


class ContextMapperServiceImpl : ContextMapperService {

    lateinit var classOfT: Class<*>
    lateinit var classOfTq: Class<*>
    override fun toCtx(src: Any): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> fromCtx(json: String): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //    override fun toCtx(src: Any): String {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun <T> fromCtx(json: String): T {
//        val gson = Gson()
//        gson.fromJson<T>("sadsad",T )
//        return (T) Object()
//    }
}