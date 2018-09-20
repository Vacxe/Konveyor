package objects

data class WithEnumDataClass(val enum: DummyEnum)

enum class DummyEnum{
    ONE, TWO, THREE
}