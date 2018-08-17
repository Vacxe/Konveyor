package com.github.vacxe.konveyor

import java.lang.reflect.Constructor
import java.util.*

class Konveyor{
    companion object {
        val random by lazy {
            Random()
        }
        
        private val customObjectResolver = CustomObjectResolver()

        fun <T> build(clazz: Class<T>, constructorNumber: Int = 0): T{
            val constructors = clazz.constructors
                val constructor = constructors[constructorNumber]
                val constructorArguments = initConstructorArguments(constructor)
                return constructor.newInstance(*constructorArguments) as T
        }

        private fun initConstructorArguments(constructor: Constructor<*>): Array<Any> {
            val constructorArguments = Array<Any>(constructor.parameterCount, { it + 1 })
            val constructorParameters = constructor.parameters

            for (index in constructorParameters.indices) {
                constructorArguments[index] = getRandomValue(constructorParameters[index].type)
            }
            return constructorArguments
        }

        private fun getRandomValue(parameterType: Class<*>): Any =
            when (parameterType) {
                Byte::class.java -> random.nextInt(Byte.MAX_VALUE.toInt()).toByte()
                Short::class.java -> random.nextInt(Short.MAX_VALUE.toInt()).toShort()
                Int::class.java -> random.nextInt()
                Long::class.java -> random.nextLong()
                Float::class.java -> random.nextFloat()
                Double::class.java -> random.nextDouble()
                Boolean::class.java -> random.nextBoolean()
                Char::class.java -> random.nextInt(Byte.MAX_VALUE.toInt()).toChar()
                String::class.java -> "RandomString ${random.nextInt()}"
                else -> customObjectResolver.resolve(parameterType)!!
            }

        fun <C> addCustomType(clazz: Class<C>, lambda : () -> C) =
            customObjectResolver.addCustomType(clazz, lambda)
    }
}