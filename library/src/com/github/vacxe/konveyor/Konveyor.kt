package com.github.vacxe.konveyor

class Konveyor{
    companion object {
        internal val customObjectResolver = CustomObjectResolver()

        fun <T> addCustomType(clazz: Class<T>, lambda: () -> T) =
                customObjectResolver.addCustomType(clazz, lambda)
    }
}

fun <T> randomBuild(clazz: Class<T>, constructorNumber: Int = 0, nestedLevel: Int = 0): T =
        Generator(Konveyor.customObjectResolver).build(clazz, constructorNumber, nestedLevel)


inline fun <reified T : Any> randomBuild(nestedLevel: Int = 0): T = randomBuild(T::class.java, nestedLevel)

