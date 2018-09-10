import konveyor.base.randomBuild
import objects.ImmutableCollectionDataClass
import objects.MutableCollectionDataClass
import objects.NestedDataClass
import objects.PrimitiveDataClass
import org.junit.Test

class GenerationTest {

    @Test
    fun primiviteGenerationTest(){
       val primitiveDataClass: PrimitiveDataClass = randomBuild()

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
    fun nestedGenerationTest(){
        val nestedDataClass: NestedDataClass = randomBuild()

        assert(nestedDataClass.first != null)
        assert(nestedDataClass.second != null)
    }

    @Test
    fun mutableCollectionGenerationTest(){
        val mutableCollectionDataClass: MutableCollectionDataClass = randomBuild()

        assert(mutableCollectionDataClass.array != null)
        assert(mutableCollectionDataClass.set != null)
        assert(mutableCollectionDataClass.map != null)
    }

    @Test
    fun immutableCollectionGenerationTest(){
        val immutableCollectionDataClass: ImmutableCollectionDataClass = randomBuild()

        assert(immutableCollectionDataClass.array != null)
        assert(immutableCollectionDataClass.set != null)
        assert(immutableCollectionDataClass.map != null)
    }
}