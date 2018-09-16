# Konveyor
Konveyor is a library for generation Data classes with random values

[![Download](https://api.bintray.com/packages/vacxe2/maven/Konveyor/images/download.svg) ](https://bintray.com/vacxe2/maven/Konveyor/_latestVersion)


## Genesis

Sometime in test implimentation you should to create instance of object. It's manual job take allot of time actually and so bored. And what worst that this objects can contains nested objects. And your test will be a bit rubbish.


## How to use Konveyor

For example you have some class like this

```kotlin
data class PrimitiveDataClass(val int: Int,
                              val double: Double,
                              val byte: Byte,
                              val short: Short,
                              val long: Long,
                              val float: Float,
                              val boolean: Boolean,
                              val char: Char,
                              val string: String,
                              val charSequence: CharSequence)
```

Just call 

```kotlin
val primitiveDataClass: PrimitiveDataClass = randomBuild()
```

And all fields will be automaticaly filled

```
PrimitiveDataClass(int=-160837378, double=0.2543439936127936, byte=125, short=7553, long=8930811805778341874, float=0.9110602, boolean=true, char=2, string=String_212195448, charSequence=String_1391234678)
```

### Special cases

Konveyor will generate for you all classes that has primitives in base. But somethime we should to create classes with interfaces and some unusual cases. We can resolve this issue with `ObjectResolver`

```kotlin
val objectResolver = ObjectResolver()
objectResolver.addCustomType(MyInterface::class.java, { MyInterfaceImpl() })
val customParameters = CustomParameters(customObjectResolver = objectResolver)

val immutableCollectionDataClass: NestedInterfaceDataClass = randomBuild()
```

### Compatibility with Java

Of course you can use Konveyor with Java

```java
PrimitiveDataClass primitiveDataClass = KonveyorKt.randomBuild(PrimitiveDataClass.class);
```

### Download
Maven:

```xml
<dependency>
  <groupId>com.github.vacxe</groupId>
  <artifactId>konveyor</artifactId>
  <version>latest version</version>
  <type>pom</type>
</dependency>
```
or Gradle:

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'com.github.vacxe:konveyor:latest version'
}
```

##### Enjoy your build with Konveyor!

![Logo](https://user-images.githubusercontent.com/2812510/45409134-8b1cba00-b698-11e8-9d43-19d1f7fe19dc.png)