package com.github.vacxe.konveyor.base

import com.github.vacxe.konveyor.generate.Generator
import com.github.vacxe.konveyor.generate.configuration.Configuration

class Konveyor {
    companion object {
        val configuration: Configuration = Configuration()

        fun configuration(function: Configuration.() -> Unit){
            Konveyor.configuration.apply(function)
        }
    }
}

inline fun <reified T : Any> build(): T {
    return Generator(Konveyor.configuration).build(T::class.java)
}

inline fun <reified T : Any> build(noinline configuration: Configuration.() -> Unit): T {
    return Generator(Konveyor.configuration.apply(configuration)).build(T::class.java)
}

