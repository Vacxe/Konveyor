package com.github.vacxe.konveyor

import java.lang.reflect.Constructor
import java.util.*

internal class Generator(val customObjectResolver: CustomObjectResolver) {

    val random = Random()
    val possibleNesting = 100 //TODO: add object

    fun <T> build(clazz: Class<T>, constructorNumber: Int = 0, nestedLevel: Int = 0): T {
        if(nestedLevel > possibleNesting){
            throw Exception("Your constructor contains cycle dependency")
        }

        val constructors = clazz.constructors
        if (constructors.isNotEmpty()) {
            val constructor = constructors[constructorNumber]
            val constructorArguments = initConstructorArguments(constructor, nestedLevel)
            return constructor.newInstance(*constructorArguments) as T
        } else {
            throw Exception("Object doesn't contain public constructors")
        }
    }

    private fun initConstructorArguments(constructor: Constructor<*>, nestedLevel: Int): Array<Any> {
        val constructorArguments = Array<Any>(constructor.parameterCount, { it + 1 })
        val constructorParameters = constructor.parameters

        for (index in constructorParameters.indices) {
            constructorArguments[index] = getRandomValue(constructorParameters[index].type, nestedLevel)
        }
        return constructorArguments
    }

    private fun getRandomValue(parameterType: Class<*>, nestedLevel: Int): Any =
            when (parameterType) {
                Byte::class.java -> random.nextInt(Byte.MAX_VALUE.toInt()).toByte()
                Short::class.java -> random.nextInt(Short.MAX_VALUE.toInt()).toShort()
                Int::class.java -> random.nextInt()
                Long::class.java -> random.nextLong()
                Float::class.java -> random.nextFloat()
                Double::class.java -> random.nextDouble()
                Boolean::class.java -> random.nextBoolean()
                Char::class.java -> random.nextInt(Byte.MAX_VALUE.toInt()).toChar()
                String::class.java -> "String_${random.nextInt()}"
                CharSequence::class.java -> "String_${random.nextInt()}"
                else -> generateNestedClass(parameterType, nestedLevel + 1)
            }

    private fun generateNestedClass(clazz: Class<*>, nestedLevel: Int = 0): Any
            = customObjectResolver.resolve(clazz) ?: build(clazz, nestedLevel = nestedLevel)

}