package com.github.vacxe.konveyor.generate

import org.junit.Assert.*

class RandomPrimitiveGeneratorTest{
    private val generator = RandomPrimitiveGenerator()

    fun testInt(){
       assert(generator.isPrimivite(Int::class.java))
       assert(generator.generatePrimitive(Int::class.java) is Int)
    }
}