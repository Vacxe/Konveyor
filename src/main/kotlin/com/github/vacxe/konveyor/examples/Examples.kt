package com.github.vacxe.konveyor.examples

import konveyor.base.randomBuild

fun main(args: Array<String>) {
    val arraysData: ArraysData = randomBuild()
    print(arraysData)
}

data class ArraysData(val array: List<Int>)