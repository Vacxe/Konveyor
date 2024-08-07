# Konveyor
Konveyor is a library for generating Data classes with random values.

## Motivation

From time to time, when you implement a test, you need an instance of object. Creating an instance manually takes a lot of time and it might be boring. It's even worse if object contains nested objects. It makes your test a bit littered.


## How to use Konveyor

For example, you have some class like this

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

Just call this:

```kotlin
val primitiveDataClass: PrimitiveDataClass = randomBuild()
```

And all fields are automatically filled up:

```
PrimitiveDataClass(int=-160837378, double=0.2543439936127936, byte=125, short=7553, long=8930811805778341874, float=0.9110602, boolean=false, char=2, string=String_212195448, charSequence=String_1391234678)
```

All ```boolean``` will be ```false``` by default

Also, all fields with setter functions should be initialized manualy. Library can help you only with initialization for constructors variables. 

### Special cases

Konveyor generates for you every class which has primitives in base. But sometimes, we have to create classes with interfaces and with some irregular cases. We can resolve this issue by using `ObjectResolver`:

```kotlin
val resolver = ObjectResolver()
resolver(MyInterface::class.java, { MyInterfaceImpl() })

val nestedInterfaceDataClass: NestedInterfaceDataClass = randomBuild(resolver = resolver)
```

### Compatibility with Java

Of course, you can use Konveyor with Java:

```java
PrimitiveDataClass primitiveDataClass = KonveyorKt.randomBuild(PrimitiveDataClass.class);
```

### Download
Maven:

```xml
<dependency>
  <groupId>io.github.vacxe</groupId>
  <artifactId>konveyor</artifactId>
  <version>latest version</version>
  <type>pom</type>
</dependency>
```
Gradle:

```groovy
dependencies {
    implementation 'io.github.vacxe:konveyor:<LATEST VERSION>'
}
```
```kotlin
dependencies {
    implementation("io.github.vacxe:konveyor:<LATEST VERSION>")
}
```

##### Enjoy your build with Konveyor!

![Logo](https://user-images.githubusercontent.com/2812510/45409134-8b1cba00-b698-11e8-9d43-19d1f7fe19dc.png)
