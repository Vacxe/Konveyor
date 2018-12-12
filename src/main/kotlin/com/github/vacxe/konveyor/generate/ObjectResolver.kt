package konveyor.generate

class ObjectResolver {
    private val customObjectResolverMap = HashMap<Class<*>, () -> Any>()

    fun <C: Any> addCustomType(clazz: Class<C>, lambda: () -> C) : ObjectResolver {
        customObjectResolverMap[clazz] = lambda
        return this
    }

    inline fun <reified C: Any> addCustomType(noinline lambda : () -> C) : ObjectResolver {
        return addCustomType(C::class.java, lambda)
    }

    internal fun resolve(clazz: Class<*>): Any? {
        val customResolver = customObjectResolverMap[clazz]
        return customResolver?.invoke()
    }

    internal fun merge(objectResolver: ObjectResolver?): ObjectResolver {
        objectResolver?.let{
            customObjectResolverMap.putAll(it.customObjectResolverMap)
        }
        return this
    }
}
