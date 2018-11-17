package com.github.vacxe.konveyor.generate

import konveyor.exceptions.KonveyorException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class InterfaceGenerator {
    fun isInterface(parameterType: Class<*>) = parameterType.isInterface

    fun generateMock(parameterType: Class<*>) = Proxy.newProxyInstance(InterfaceGenerator::class.java.classLoader,
            arrayOf(parameterType),
            InvocationHandler { _, method, _ ->
                val name = method.name
                if (name == "toString") {
                    return@InvocationHandler "It's a mock interface for ${parameterType.name} class"
                }
                throw KonveyorException("no method found")
            }
    )
}