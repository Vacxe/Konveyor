package io.github.vacxe.konveyor.generate

import io.github.vacxe.konveyor.exceptions.KonveyorException
import java.util.*

internal class PrimitiveGenerator {
    private val random = Random()

    private val primitiveSet = setOf(Byte::class.java, Short::class.java, Int::class.java,
            Long::class.java, Float::class.java, Double::class.java, Boolean::class.java,
            Char::class.java, String::class.java, CharSequence::class.java
    )

    fun isPrimitive(parameterType: Class<*>) = primitiveSet.contains(parameterType)

    fun generatePrimitive(parameterType: Class<*>): Any = when (parameterType) {
        Byte::class.java -> random.nextInt(Byte.MAX_VALUE.toInt()).toByte()
        Short::class.java -> random.nextInt(Short.MAX_VALUE.toInt()).toShort()
        Int::class.java -> random.nextInt()
        Long::class.java -> random.nextLong()
        Float::class.java -> random.nextFloat()
        Double::class.java -> random.nextDouble()
        Boolean::class.java -> false
        Char::class.java -> random.nextInt(Byte.MAX_VALUE.toInt()).toChar()
        String::class.java -> "String_${random.nextInt()}"
        CharSequence::class.java -> "String_${random.nextInt()}"
        else -> KonveyorException("Primivite type not declared")
    }
}
