package com.github.vacxe.konveyor.generate.configuration

data class ClassConfiguration<T>(val type: Class<T>) {
    var provider: () -> T? = { null }
}
