package com.github.vacxe.konveyor.generate

import com.github.vacxe.konveyor.exceptions.KonveyorException
import com.github.vacxe.konveyor.generate.configuration.Configuration
import java.lang.reflect.Constructor

@Suppress("UNCHECKED_CAST")
class Generator(private val configuration: Configuration) {
    private val randomPrimitiveGenerator = PrimitiveGenerator()
    private val randomCollectionsGenerator = ImmutableCollectionGenerator()
    private val enumGenerator = EnumGenerator()
    private val interfaceGenerator = InterfaceGenerator()

    fun <T> build(clazz: Class<T>, nestedLevel: Int = 0): T {
        if (nestedLevel > configuration.nesting) {
            throw KonveyorException("Generation level out of possible nesting")
        }

        val constructors = clazz.constructors
        constructors.sortBy { it.parameterCount }
        if (constructors.isNotEmpty()) {
            for (constructor in constructors) {
                try {
                    val constructor = constructors[0]
                    val constructorArguments = initConstructorArguments(constructor, nestedLevel)
                    return constructor.newInstance(*constructorArguments) as T
                } catch (e: Exception) {
                    System.out.print(e.message)
                }
            }
            throw KonveyorException("Impossible to create $clazz")
        } else {
            throw KonveyorException("$clazz doesn't contain public constructors")
        }
    }

    private fun initConstructorArguments(constructor: Constructor<*>,
                                         nestedLevel: Int): Array<Any> {
        val constructorArguments = Array<Any>(constructor.parameterCount) { it + 1 }
        val constructorParameters = constructor.parameters

        for (index in constructorParameters.indices) {
            val specificValue = configuration.propertyConfigurations.find {
                constructorParameters[index].type == it.type && constructorParameters[index].name == it.name
            }?.provider?.invoke()

            constructorArguments[index] = specificValue ?: getRandomValue(constructorParameters[index].type, nestedLevel)
        }
        return constructorArguments
    }

    private fun getRandomValue(parameterType: Class<*>, nestedLevel: Int): Any =
            when {
                randomPrimitiveGenerator.isPrimitive(parameterType) -> randomPrimitiveGenerator.generatePrimitive(parameterType)
                randomCollectionsGenerator.isImmutableCollection(parameterType) -> randomCollectionsGenerator.generateCollection(parameterType)
                enumGenerator.isEnum(parameterType) -> enumGenerator.generateEnum(parameterType)
                interfaceGenerator.isInterface(parameterType) -> interfaceGenerator.generateMock(parameterType)
                else -> generateNestedClass(parameterType, nestedLevel + 1)
            }

    private fun generateNestedClass(clazz: Class<*>, nestedLevel: Int = 0): Any {
        val providedValue =
                configuration.classConfigurations
                        .find { it.type == clazz }
                        ?.provider
                        ?.invoke()
        if(providedValue != null) {
            return providedValue
        }
        for (constructorNumber in 0..clazz.constructors.size) {
            try {
                return build(clazz, nestedLevel)
            } catch (e: KonveyorException) {
            }
        }
        throw KonveyorException("No possible to create constructor build tree")
    }
}
