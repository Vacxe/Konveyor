package com.github.vacxe.konveyor

class Konveyor{
    companion object {
        internal val customObjectResolver = CustomObjectResolver()

        fun <T> addCustomType(clazz: Class<T>, lambda: () -> T) =
                customObjectResolver.addCustomType(clazz, lambda)
    }
}

fun <T> build(clazz: Class<T>, constructorNumber: Int = 0, nestedLevel: Int = 0): T =
        Generator(Konveyor.customObjectResolver).build(clazz, constructorNumber, nestedLevel)


inline fun <reified T : Any> build(nestedLevel: Int = 0): T = build(T::class.java, nestedLevel)

