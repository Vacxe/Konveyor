package objects

interface InterfaceType

class MultipleConstructorsClass(val int: Int, val interf: InterfaceType, val byte: Byte) {
    constructor(int: Int, interf: InterfaceType) : this(int, interf, Byte.MAX_VALUE)
}