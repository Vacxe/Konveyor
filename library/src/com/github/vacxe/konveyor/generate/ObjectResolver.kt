package com.github.vacxe.konveyor.generate

class ObjectResolver {
    private val customObjectResolverMap = HashMap<Class<*>, (Any)>()

    fun <C> addCustomType(clazz: Class<C>, lambda : () -> C) =
        customObjectResolverMap.put(clazz, lambda)

    internal fun resolve(clazz: Class<*>) =
         (customObjectResolverMap.getOrDefault(clazz, null) as? () -> Any)?.invoke()

    internal fun merge(objectResolver: ObjectResolver): ObjectResolver{
        customObjectResolverMap.putAll(objectResolver.customObjectResolverMap)
        return this
    }
}
