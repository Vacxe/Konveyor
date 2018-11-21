package konveyor.generate

class ObjectResolver {
    private val customObjectResolverMap = HashMap<Class<*>, () -> Any>()

    fun <C: Any> addCustomType(clazz: Class<C>, lambda: () -> C) {
        customObjectResolverMap[clazz] = lambda
    }

    inline fun <reified C: Any> addCustomType(noinline lambda : () -> C) {
        addCustomType(C::class.java, lambda)
    }

    internal fun resolve(clazz: Class<*>): Any? {
        val customResolver = customObjectResolverMap[clazz]
        return customResolver?.invoke()
    }

    internal fun merge(objectResolver: ObjectResolver): ObjectResolver {
        customObjectResolverMap.putAll(objectResolver.customObjectResolverMap)
        return this
    }
}
