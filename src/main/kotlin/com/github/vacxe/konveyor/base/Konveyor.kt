package konveyor.base

import konveyor.generate.ObjectResolver
import konveyor.generate.CustomParameters
import konveyor.generate.Generator

class Konveyor {
    companion object {
        internal val objectResolver = ObjectResolver()

        fun <T> addCustomType(clazz: Class<T>, lambda: () -> T) =
                objectResolver.addCustomType(clazz, lambda)
    }
}

fun <T> randomBuild(clazz: Class<T>): T = randomBuild(clazz = clazz)

fun <T> randomBuild(clazz: Class<T>, constructorNumber: Int): T = randomBuild(clazz = clazz, constructorNumber = constructorNumber)

fun <T> randomBuild(clazz: Class<T>, constructorNumber: Int = 0,
                    customParameters: CustomParameters = CustomParameters()): T =
        Generator(Konveyor.objectResolver.merge(customParameters.customObjectResolver),
                customParameters).build(clazz, constructorNumber)

inline fun <reified T : Any> randomBuild(constructorNumber: Int = 0, customParameters: CustomParameters = CustomParameters()): T =
        randomBuild(T::class.java, constructorNumber, customParameters)
