package examples

import com.github.vacxe.konveyor.base.Konveyor
import com.github.vacxe.konveyor.base.randomBuild


fun main(args: Array<String>) {
    Konveyor.addCustomType(StringBuilder::class.java) {
         StringBuilder().append(123)
    }

    val primitive: PrimitiveDataClass = randomBuild()
    System.out.println(primitive.toString())

    val nested = randomBuild(NestedDataClass::class.java)
    System.out.println(nested.toString())
}