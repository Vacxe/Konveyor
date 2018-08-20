package com.github.vacxe.konveyor.generate

import com.github.vacxe.konveyor.exceptions.KonveyorException
import java.lang.reflect.Constructor

internal class Generator(private val customObjectResolver: ObjectResolver = ObjectResolver(),
                         private val parameters: CustomParameters = CustomParameters()) {

    private val randomPrimitiveGenerator = RandomPrimitiveGenerator()

    fun <T> build(clazz: Class<T>, constructorNumber: Int = 0, nestedLevel: Int = 0): T {
        if(nestedLevel > parameters.nesting){
            throw KonveyorException("Generation level out of possible nesting")
        }

        if(clazz.isInterface){
            //TODO: Mock
        }

        val constructors = clazz.constructors
        if (constructors.isNotEmpty()) {
            val constructor = constructors[constructorNumber]
            val constructorArguments = initConstructorArguments(constructor, nestedLevel)
            return constructor.newInstance(*constructorArguments) as T
        } else {
            throw KonveyorException("Object doesn't contain public constructors")
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
                if (randomPrimitiveGenerator.isPrimivite(parameterType))
                    randomPrimitiveGenerator.generatePrimitive(parameterType)
                else generateNestedClass(parameterType, nestedLevel + 1)


    private fun generateNestedClass(clazz: Class<*>, nestedLevel: Int = 0): Any {
        customObjectResolver.resolve(clazz)?.let { return it }
        for(constructorNumber in 0..clazz.constructors.size){
            try {
                return build(clazz, constructorNumber, nestedLevel = nestedLevel)
            }catch (e: KonveyorException){ }
        }
        throw KonveyorException("No possible to create constructor build tree")
    }
}
