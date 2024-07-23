package io.github.vacxe.konveyor.generate

import io.github.vacxe.konveyor.exceptions.KonveyorException
import java.util.*

class ImmutableCollectionGenerator {
    fun isImmutableCollection(parameterType: Class<*>) = (
            List::class.java.isAssignableFrom(parameterType)
                    || Map::class.java.isAssignableFrom(parameterType)
                    || Set::class.java.isAssignableFrom(parameterType)
                    || Queue::class.java.isAssignableFrom(parameterType))
            && parameterType.isInterface

    fun generateCollection(parameterType: Class<*>): Any =
            when {
                List::class.java.isAssignableFrom(parameterType) -> Collections.EMPTY_LIST
                Map::class.java.isAssignableFrom(parameterType) -> Collections.EMPTY_MAP
                Set::class.java.isAssignableFrom(parameterType) -> Collections.EMPTY_SET
                Queue::class.java.isAssignableFrom(parameterType) -> LinkedList<Any>()
                else -> KonveyorException("Collection type not declared")
            }
}
