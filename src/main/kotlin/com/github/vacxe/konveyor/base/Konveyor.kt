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
fun <T> randomBuild(clazz: Class<T>): T = randomBuild(clazz, 0)

fun <T> randomBuild(clazz: Class<T>, constructorNumber: Int): T = randomBuild(clazz, constructorNumber, CustomParameters())

fun <T> randomBuild(clazz: Class<T>, constructorNumber: Int, customParameters: CustomParameters): T =
        Generator(Konveyor.objectResolver.merge(customParameters.customObjectResolver),
                customParameters).build(clazz, constructorNumber)

inline fun <reified T : Any> randomBuild(constructorNumber: Int = 0, customParameters: CustomParameters = CustomParameters()): T =
        randomBuild(T::class.java, constructorNumber, customParameters)
