package com.github.vacxe.konveyor.generate

class EnumGenerator{
    fun generateEnum(parameterType: Class<*>): Any = parameterType.enumConstants.first()

    fun isEnum(parameterType: Class<*>) = parameterType.isEnum
}