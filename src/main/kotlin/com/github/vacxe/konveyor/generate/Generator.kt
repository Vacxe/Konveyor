package konveyor.generate

import com.github.vacxe.konveyor.generate.ImmutableCollectionGenerator
import konveyor.exceptions.KonveyorException
import java.lang.reflect.Constructor

internal class Generator(private val customObjectResolver: ObjectResolver = ObjectResolver(),
                         private val parameters: CustomParameters = CustomParameters()) {

    private val randomPrimitiveGenerator = PrimitiveGenerator()
    private val randomCollectionsGenerator = ImmutableCollectionGenerator()

    fun <T> build(clazz: Class<T>, constructorNumber: Int = 0, nestedLevel: Int = 0): T {
        if (nestedLevel > parameters.nesting) {
            throw KonveyorException("Generation level out of possible nesting")
        }

        val constructors = clazz.constructors
        if (constructors.isNotEmpty()) {
            for (constructor in constructors) {
                try {
                    val constructor = constructors[constructorNumber]
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

    private fun initConstructorArguments(constructor: Constructor<*>, nestedLevel: Int): Array<Any> {
        val constructorArguments = Array<Any>(constructor.parameterCount, { it + 1 })
        val constructorParameters = constructor.parameters

        for (index in constructorParameters.indices) {
            constructorArguments[index] = getRandomValue(constructorParameters[index].type, nestedLevel)
        }
        return constructorArguments
    }

    private fun getRandomValue(parameterType: Class<*>, nestedLevel: Int): Any =
            when {
                randomPrimitiveGenerator.isPrimivite(parameterType) -> randomPrimitiveGenerator.generatePrimitive(parameterType)
                randomCollectionsGenerator.isImmutableCollection(parameterType) -> randomCollectionsGenerator.generateCollection(parameterType)
                else -> generateNestedClass(parameterType, nestedLevel + 1)
            }


    private fun generateNestedClass(clazz: Class<*>, nestedLevel: Int = 0): Any {
        customObjectResolver.resolve(clazz)?.let { return it }
        for (constructorNumber in 0..clazz.constructors.size) {
            try {
                return build(clazz, constructorNumber, nestedLevel = nestedLevel)
            } catch (e: KonveyorException) {
            }
        }
        throw KonveyorException("No possible to create constructor build tree")
    }
}
