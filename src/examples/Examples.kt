package examples

import com.github.vacxe.konveyor.Konveyor
import com.github.vacxe.konveyor.build


fun main(args: Array<String>) {

    Konveyor.addCustomType(StringBuilder::class.java) {
         StringBuilder().append(123)
    }

    val primitive: PrimitiveDataClass = build()
    System.out.println(primitive.toString())

    val nested = build(NestedDataClass::class.java)
    System.out.println(nested.toString())
}