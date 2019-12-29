package com.github.vacxe.konveyor.generate.configuration

class Configuration {

    var nesting = 100
    val propertyConfigurations: HashSet<PropertyConfiguration<*>> = hashSetOf()
    val classConfigurations: HashSet<ClassConfiguration<*>> = hashSetOf()

    inline fun <reified T : Any> forProperty(name: String, propertyProvider: PropertyConfiguration<T>.() -> Unit) {
        propertyConfigurations.add(PropertyConfiguration(T::class.java, name).apply(propertyProvider))
    }

    inline fun <reified T : Any> forClass(classConfiguration: ClassConfiguration<T>.() -> Unit) {
        classConfigurations.add(ClassConfiguration(T::class.java).apply(classConfiguration))
    }
}