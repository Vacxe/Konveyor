package com.github.vacxe.konveyor.generate.configuration

data class PropertyConfiguration<T>(val type: Class<T>,
                                    val name: String) {
    var provider: () -> T? = { null }
}
