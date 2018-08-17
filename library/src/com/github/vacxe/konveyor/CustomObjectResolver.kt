package com.github.vacxe.konveyor

internal class CustomObjectResolver {
    private val customObjectResolverMap = HashMap<Class<*>, (Any)>()

    fun <C> addCustomType(clazz: Class<C>, lambda : () -> C) =
        customObjectResolverMap.put(clazz, lambda)

    fun resolve(clazz: Class<*>) =
         (customObjectResolverMap.getOrDefault(clazz, null) as? () -> Any)?.invoke()

}
