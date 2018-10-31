package com.github.vacxe.konveyor.generate

class EnumGenerator{
    fun generateEnum(parameterType: Class<*>): Any {
        val possibleValues = parameterType.enumConstants
        return possibleValues[(System.currentTimeMillis() % possibleValues.size).toInt()]
    }

    fun isEnum(parameterType: Class<*>) = parameterType.isEnum
}