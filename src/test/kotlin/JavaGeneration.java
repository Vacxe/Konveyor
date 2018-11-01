import konveyor.base.KonveyorKt;
import objects.PrimitiveDataClass;
import org.junit.Test;

public class JavaGeneration {
    @Test
    public void testPrimitiveGenerationWithJava(){
        PrimitiveDataClass primitiveDataClass = KonveyorKt.randomBuild(PrimitiveDataClass.class);
    }
}
