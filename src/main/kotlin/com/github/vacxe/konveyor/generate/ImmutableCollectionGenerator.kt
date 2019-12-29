package com.github.vacxe.konveyor.generate

import com.github.vacxe.konveyor.exceptions.KonveyorException
import java.util.*

class ImmutableCollectionGenerator {
    fun isImmutableCollection(parameterType: Class<*>) =
            List::class.java.isAssignableFrom(parameterType)
                    || Map::class.java.isAssignableFrom(parameterType)
                    || Set::class.java.isAssignableFrom(parameterType)
            && parameterType.isInterface

    fun generateCollection(parameterType: Class<*>): Any =
            when {
                List::class.java.isAssignableFrom(parameterType) -> Collections.EMPTY_LIST
                Map::class.java.isAssignableFrom(parameterType) -> Collections.EMPTY_MAP
                Set::class.java.isAssignableFrom(parameterType) -> Collections.EMPTY_SET
                else -> KonveyorException("Collection type not declared")
            }
}
