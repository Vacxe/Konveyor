
import io.github.vacxe.konveyor.base.Konveyor
import io.github.vacxe.konveyor.base.randomBuild
import io.github.vacxe.konveyor.exceptions.KonveyorException
import io.github.vacxe.konveyor.generate.ObjectResolver
import objects.*
import org.junit.Test
import java.lang.Exception

class GenerationTest {

    @Test
    fun primitiveGenerationTest() {
        val primitiveDataClass: PrimitiveDataClass = randomBuild()
        println(primitiveDataClass)

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

    @Test(expected = KonveyorException::class)
    fun nestedGenerationException() {
        val nestedDataClass: LoopNestedDataClass = randomBuild()
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
    fun enumGenerationTest() {
        val enumDataClass: WithEnumDataClass = randomBuild()

        assert(enumDataClass.enum != null)
    }

    @Test
    fun multipleConstructorsTest() {
        var generated: MultipleConstructorsClass = randomBuild()

        assert(generated.byte == Byte.MAX_VALUE)
        assert(generated.int != null)
        assert(generated.interf != null)
    }

    @Test
    fun nestedInterfaceGeneration() {
        val generated: MultipleConstructorsClass = randomBuild()
        assert(generated.interf != null)
        generated.interf.toString()
    }

    @Test(expected = Exception::class)
    fun nestedInterfaceGenerationNoMethodFound() {
        val generated: MultipleConstructorsClass = randomBuild()
        assert(generated.interf != null)
        generated.interf.hashCode()
    }

    @Test
    fun interfaceGenerationWithImplementation() {
        val objectResolver = ObjectResolver()
        objectResolver.addCustomType<MyInterface> {
            MyInterfaceImpl()
        }

        val nestedInterfaceDataClass: NestedInterfaceDataClass = randomBuild(resolver = objectResolver)
    }

    @Test
    fun interfaceGenerationWithoutEffectiveImplementation() {
        val objectResolver = ObjectResolver()
        Konveyor.addCustomType(MyInterfaceImpl::class.java) { MyInterfaceImpl() }
        objectResolver.addCustomType { MyInterfaceImpl() }
        val nestedInterfaceDataClass: NestedInterfaceDataClass = randomBuild(resolver = objectResolver)
    }
}