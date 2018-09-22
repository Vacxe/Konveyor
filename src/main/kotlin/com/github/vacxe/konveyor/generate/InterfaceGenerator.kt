package com.github.vacxe.konveyor.generate

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class InterfaceGenerator {
    fun inInterface(parameterType: Class<*>) = parameterType.isInterface

    fun generateMock(parameterType: Class<*>)
            = Proxy.newProxyInstance(InterfaceGenerator::class.java.classLoader, arrayOf(parameterType), object : InvocationHandler {
        override fun invoke(p0: Any?, method: Method, p2: Array<out Any>?): Any {
            val name = method.name
            if (name == "toString") {
                return "It's a mock interface for ${parameterType.name} class"
            }
            throw  RuntimeException("no method found")
        }
    })
}