import konveyor.base.randomBuild
import konveyor.generate.CustomParameters
import konveyor.generate.ObjectResolver
import objects.*
import org.junit.Test

class GenerationTest {

    @Test
    fun primiviteGenerationTest() {
        val primitiveDataClass: PrimitiveDataClass = randomBuild()
        System.out.print(primitiveDataClass)

        assert(primitiveDataClass.boolean != null)
        assert(primitiveDataClass.byte != null)
        assert(primitiveDataClass.char != null)
        assert(primitiveDataClass.charSequence != null)
        assert(primitiveDataClass.double != null)
        assert(primitiveDataClass.float != null)
        assert(primitiveDataClass.int != null)
        assert(primitiveDataClass.long != null)
        assert(primitiveDataClass.short != null)
        assert(primitiveDataClass.string != null)

    }

    @Test
    fun nestedGenerationTest() {
        val nestedDataClass: NestedDataClass = randomBuild()

        assert(nestedDataClass.first != null)
        assert(nestedDataClass.second != null)
    }

    @Test
    fun mutableCollectionGenerationTest() {
        val mutableCollectionDataClass: MutableCollectionDataClass = randomBuild()

        assert(mutableCollectionDataClass.array != null)
        assert(mutableCollectionDataClass.set != null)
        assert(mutableCollectionDataClass.map != null)
    }

    @Test
    fun immutableCollectionGenerationTest() {
        val immutableCollectionDataClass: ImmutableCollectionDataClass = randomBuild()

        assert(immutableCollectionDataClass.array != null)
        assert(immutableCollectionDataClass.set != null)
        assert(immutableCollectionDataClass.map != null)
    }

    @Test
    fun interfaceGeneration() {
        val objectResolver = ObjectResolver()
        objectResolver.addCustomType(MyInterface::class.java, { MyInterfaceImpl() })
        val customParameters = CustomParameters(customObjectResolver = objectResolver)

        val immutableCollectionDataClass: NestedInterfaceDataClass = randomBuild(customParameters = customParameters)
    }
}